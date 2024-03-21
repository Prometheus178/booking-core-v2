package org.booking.core.service.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.BusinessEntityNotFoundException;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.BusinessHours;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.response.ReservationResponse;
import org.booking.core.lock.RedisDistributedLock;
import org.booking.core.mapper.ReservationMapper;
import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.repository.ReservationRepository;
import org.booking.core.repository.ReservationScheduleRepository;
import org.booking.core.repository.UserReservationHistoryRepository;
import org.booking.core.service.UserService;
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

@RequiredArgsConstructor
@Log
@Service
public class AppointmentSchedulerServiceBean implements AppointmentSchedulerService{

    public static final String RESERVED = "Reserved";
    private final ReservationRepository reservationRepository;
    private final BusinessServiceRepository businessServiceRepository;
    private final CachingAppointmentSchedulerService cachingAppointmentSchedulerService;
    private final UserReservationHistoryRepository userReservationHistoryRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationScheduleRepository reservationScheduleRepository;
    private final RedisDistributedLock redisDistributedLock;
    private final UserService userService;

    @Override
    public List<TimeSlot> findAvailableSlots( Long businessServiceId, LocalDate date) {
		Optional<BusinessServiceEntity> businessService = businessServiceRepository.findById(businessServiceId);
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
			throw new BusinessEntityNotFoundException(BusinessServiceEntity.ENTITY_NAME, businessServiceId);
        }
    }

    @Override
    public ReservationResponse reserve(ReservationRequest reservationRequest) {
		Reservation reservation = reservationMapper.toEntity(reservationRequest);
        return reserve(reservation);
    }



    @Override
    public ReservationResponse modifyReservation(Long reservationId, ReservationRequest reservationRequest) {
        Optional<Reservation> existOptional = reservationRepository.findById(reservationId);
        if (existOptional.isPresent()){
			Reservation reservation = reservationMapper.toEntity(reservationRequest);
            String lockName = KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
					reservation.getBusinessServiceEntity().getId(), computeTimeSlot(reservation.getDuration()));
            RLock lock = redisDistributedLock.getLock(lockName);
            try {
                boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
                if (locked) {
                    log.info("Locked: " + lockName);
                    Reservation existReservation = existOptional.get();
                    existReservation.setCanceled(true);
                    ReservationResponse newReservation = reserve(reservation);
					updateTimeSlotsInCache(existReservation.getBusinessServiceEntity().getId(), existReservation.getDuration(),
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

    @Override
    public boolean cancelReservation(Long reservationId) {
        Optional<Reservation> existOptional = reservationRepository.findById(reservationId);
        if (existOptional.isPresent()) {
            Reservation existReservation = existOptional.get();
            existReservation.setCanceled(true);
            return true;
        } else {
            return false;
        }
    }

    private ReservationResponse reserve(Reservation reservation) {
        User currentUser = userService.getCurrentUser();
        reservation.setCustomer(currentUser);

        String lockName = KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
				reservation.getBusinessServiceEntity().getId(), computeTimeSlot(reservation.getDuration()));
        RLock lock = redisDistributedLock.getLock(lockName);
        try {
            boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
            if (locked) {
                log.info("Locked: " + lockName);
                String key = KeyUtil.generateKey(reservation.getBookingTime().toLocalDate(),
                        reservation.getBusinessServiceEntity().getId());
                cachingAppointmentSchedulerService.removeTimeSlotByKey(key, computeTimeSlot(reservation.getDuration()));
                Reservation savedReservation = reservationRepository.save(reservation);
                addReservationToBusinessSchedule(savedReservation);
                saveToUserHistory(savedReservation, savedReservation.getCustomer());
                saveToUserHistory(savedReservation, savedReservation.getEmployee());
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
		Business business = savedReservation.getBusinessServiceEntity().getBusiness();
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

    private void saveToUserHistory(Reservation savedReservation, User user) {
        UserReservationHistory userReservationHistory =
                userReservationHistoryRepository.findById(user.getId())
                        .orElse(new UserReservationHistory());
        userReservationHistory.addReservation(savedReservation);
        userReservationHistory.setUser(user);
        userReservationHistoryRepository.save(userReservationHistory);
    }

    private  TimeSlot computeTimeSlot(Duration duration) {
        LocalDateTime startTime = duration.getStartTime();
        LocalDateTime endTime = duration.getEndTime();
        return new TimeSlot(startTime.toLocalTime(), endTime.toLocalTime());
    }

	private List<TimeSlot> computeTimeSlots(BusinessServiceEntity businessServiceEntity) {
		int duration = businessServiceEntity.getDuration();
		Business business = businessServiceEntity.getBusiness();
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
