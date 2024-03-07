package org.booking.core.service.appointment;

import lombok.extern.java.Log;
import org.booking.core.BusinessEntityNotFoundException;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.BusinessHours;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.lock.RedisDistributedLock;
import org.booking.core.mapper.ReservationMapper;
import org.booking.core.repository.*;
import org.booking.core.service.appointment.cache.CachingAppointmentSchedulerService;
import org.booking.core.util.KeyUtil;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Log
@Service
public class AppointmentSchedulerServiceBean implements AppointmentSchedulerService{

    public static final String RESERVED = "Reserved";
    private final ReservationRepository reservationRepository;
    private final BusinessServiceRepository businessServiceRepository;
    private final CachingAppointmentSchedulerService cachingAppointmentSchedulerService;
    private final CustomerReservationHistoryRepository customerReservationHistoryRepository;
    private final EmployeeReservationHistoryRepository employeeReservationHistoryRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationScheduleRepository reservationScheduleRepository;
    private final RedisDistributedLock redisDistributedLock;

    public AppointmentSchedulerServiceBean(ReservationRepository reservationRepository,
                                           BusinessServiceRepository businessServiceRepository,
                                           CachingAppointmentSchedulerService cachingAppointmentSchedulerService,
                                           CustomerReservationHistoryRepository customerReservationHistoryRepository,
                                           EmployeeReservationHistoryRepository employeeReservationHistoryRepository,
                                           ReservationMapper reservationMapper,
                                           ReservationScheduleRepository reservationScheduleRepository, RedisDistributedLock redisDistributedLock) {
        this.reservationRepository = reservationRepository;
        this.businessServiceRepository = businessServiceRepository;
        this.cachingAppointmentSchedulerService = cachingAppointmentSchedulerService;
        this.customerReservationHistoryRepository = customerReservationHistoryRepository;
        this.employeeReservationHistoryRepository = employeeReservationHistoryRepository;
        this.reservationMapper = reservationMapper;
        this.reservationScheduleRepository = reservationScheduleRepository;
        this.redisDistributedLock = redisDistributedLock;
    }


    @Override
    public List<TimeSlot> findAvailableSlots( Long businessServiceId, LocalDate date) {
       Optional<BusinessService> businessService = businessServiceRepository.findById(businessServiceId);
        if (businessService.isPresent()){
            List<TimeSlot> availableTimeSlotsByDay =
                    cachingAppointmentSchedulerService.findAvailableTimeSlotsByKey(KeyUtil.generateKey(date,
                            businessServiceId));
            if (availableTimeSlotsByDay.isEmpty()){
                List<TimeSlot> availableTimeSlots = computeTimeSlots(businessService.get());
                cachingAppointmentSchedulerService.saveAvailableTimeSlotsByKey(KeyUtil.generateKey(date,
                        businessServiceId), availableTimeSlots);
                return availableTimeSlots;
            }
            return availableTimeSlotsByDay;
        } else {
            throw new BusinessEntityNotFoundException(BusinessService.ENTITY_NAME, businessServiceId);
        }
    }

    @Override
    public ReservationDto reserve(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        return reserve(reservation);
    }



    @Override
    public ReservationDto modifyReservation(Long reservationId, ReservationDto reservationDto) {
        Optional<Reservation> existOptional = reservationRepository.findById(reservationId);
        if (existOptional.isPresent()){
            Reservation reservation = reservationMapper.toEntity(reservationDto);
            String lockName = KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
                    reservation.getService().getId(), computeTimeSlot(reservation.getDuration()));
            RLock lock = redisDistributedLock.getLock(lockName);
            try {
                boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
                if (locked) {
                    log.info("Locked: " + lockName);
                    Reservation existReservation = existOptional.get();
                    existReservation.setCanceled(true);
                    ReservationDto newReservation = reserve(reservation);
                    updateTimeSlotsInCache(existReservation.getService().getId(), existReservation.getDuration(),
                            reservation.getDuration(),
                            reservation.getBookingTime().toLocalDate());
                    return newReservation;
                } else {
                    throw new RuntimeException(RESERVED);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            throw new BusinessEntityNotFoundException(Reservation.ENTITY_NAME, reservationId);
        }
    }

    private ReservationDto reserve(Reservation reservation) {

        String lockName = KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
                reservation.getService().getId(), computeTimeSlot(reservation.getDuration()));
        RLock lock = redisDistributedLock.getLock(lockName);
        try {
            boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
            if (locked) {
                log.info("Locked: " + lockName);
                cachingAppointmentSchedulerService.removeTimeSlotByKey(KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
                        reservation.getService().getId()), computeTimeSlot(reservation.getDuration()));
                Reservation savedReservation = reservationRepository.save(reservation);
                addReservationToBusinessSchedule(savedReservation);
                addReservationToEmployeeSchedule(savedReservation);
                addReservationToCustomerSchedule(savedReservation);
                return reservationMapper.toDto(savedReservation);
            } else {
                throw new RuntimeException(RESERVED);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Unlocked: " + lockName);
            lock.unlock();
        }
    }

    private void addReservationToBusinessSchedule(Reservation savedReservation) {
        Business business = savedReservation.getService().getBusiness();
        Optional<ReservationSchedule> reservationScheduleByBusinessId = reservationScheduleRepository.findByBusinessId(business.getId());
        if (reservationScheduleByBusinessId.isPresent()) {
            ReservationSchedule reservationSchedule = reservationScheduleByBusinessId.get();
            reservationSchedule.addReservation(savedReservation);
        } else {
            ReservationSchedule reservationSchedule = new ReservationSchedule();
            reservationSchedule.setBusiness(business);
            reservationSchedule.addReservation(savedReservation);
            reservationScheduleRepository.save(reservationSchedule);
        }
    }

    private void addReservationToEmployeeSchedule(Reservation savedReservation) {
        Customer customer = savedReservation.getCustomer();
        Optional<UserReservationHistory> optionalCustomerReservationHistory = customerReservationHistoryRepository.findByCustomerId(customer.getId());
        if (optionalCustomerReservationHistory.isPresent()) {
            UserReservationHistory reservationHistory = optionalCustomerReservationHistory.get();
            reservationHistory.addReservation(savedReservation);
        } else {
            UserReservationHistory reservationHistory = new UserReservationHistory();
            reservationHistory.setUser(customer);
            reservationHistory.addReservation(savedReservation);
            customerReservationHistoryRepository.save(reservationHistory);
        }
    }

    private void addReservationToCustomerSchedule(Reservation savedReservation) {
        Employee employee = savedReservation.getEmployee();
        Optional<EmployeeReservationHistory> optionalEmployeeReservationHistory = employeeReservationHistoryRepository.findByEmployeeId(employee.getId());
        if (optionalEmployeeReservationHistory.isPresent()) {
            EmployeeReservationHistory reservationHistory = optionalEmployeeReservationHistory.get();
            reservationHistory.addReservation(savedReservation);
        } else {
            EmployeeReservationHistory reservationHistory = new EmployeeReservationHistory();
            reservationHistory.setEmployee(employee);
            reservationHistory.addReservation(savedReservation);
            employeeReservationHistoryRepository.save(reservationHistory);
        }
    }

    private void updateTimeSlotsInCache(Long businessServiceId,
                                        Duration existReservationDuration,
                                        Duration newReservationDuration,
                                        LocalDate date) {
        TimeSlot existTimeSlot = computeTimeSlot(existReservationDuration);
        TimeSlot newTimeSlot = computeTimeSlot(newReservationDuration);
        cachingAppointmentSchedulerService.removeTimeSlotByKey(KeyUtil.generateKey(date,
                businessServiceId), newTimeSlot);
        cachingAppointmentSchedulerService.addTimeSlotByKey(KeyUtil.generateKey(date,
                businessServiceId), existTimeSlot);
    }

    private  TimeSlot computeTimeSlot(Duration duration) {
        LocalDateTime startTime = duration.getStartTime();
        LocalDateTime endTime = duration.getEndTime();
        return new TimeSlot(startTime.toLocalTime(), endTime.toLocalTime());
    }

    @Override
    public boolean cancelReservation(Long reservationId) {
        Optional<Reservation> existOptional = reservationRepository.findById(reservationId);
        if (existOptional.isPresent()){
            Reservation existReservation = existOptional.get();
            existReservation.setCanceled(true);
            return true;
        } else {
            return false;
        }
    }

    private List<TimeSlot> computeTimeSlots(BusinessService businessService) {
        int duration = businessService.getDuration();
        Business business = businessService.getBusiness();
        BusinessHours businessHours = business.getBusinessHours();
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        LocalTime openTime = businessHours.getOpenTime();
        LocalTime closeTime = businessHours.getCloseTime();

        while (openTime.plusMinutes(duration).isBefore(closeTime.plusMinutes(1))) {
            LocalTime slotStart = openTime;
            LocalTime slotEnd = openTime.plusMinutes(duration);


            TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);
            availableTimeSlots.add(timeSlot);

            openTime = slotEnd;
        }
        return availableTimeSlots;
    }

}
