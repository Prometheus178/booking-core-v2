package org.booking.core.service.appointment;

import org.booking.core.BusinessEntityNotFoundException;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.BusinessHours;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.mapper.ReservationMapper;
import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.repository.ReservationRepository;
import org.booking.core.repository.ReservationScheduleRepository;
import org.booking.core.service.appointment.cache.CachingAppointmentSchedulerService;
import org.booking.core.util.KeyUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentSchedulerServiceBean implements AppointmentSchedulerService{

    private final ReservationRepository reservationRepository;
    private final BusinessServiceRepository businessServiceRepository;
    private final CachingAppointmentSchedulerService cachingAppointmentSchedulerService;
    private final ReservationMapper reservationMapper;

    public AppointmentSchedulerServiceBean(ReservationScheduleRepository reservationScheduleRepository,
                                           ReservationRepository reservationRepository, BusinessServiceRepository businessServiceRepository,
                                           CachingAppointmentSchedulerService cachingAppointmentSchedulerService,
                                           ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.businessServiceRepository = businessServiceRepository;
        this.cachingAppointmentSchedulerService = cachingAppointmentSchedulerService;
        this.reservationMapper = reservationMapper;
    }


    @Override
    public List<TimeSlot> findAvailableSlots( Long businessServiceId, LocalDate date) {
       Optional<BusinessService> businessService = businessServiceRepository.findById(businessServiceId);
        if (businessService.isPresent()){
            List<TimeSlot> availableTimeSlotsByDay =
                    cachingAppointmentSchedulerService.findAvailableTimeSlotsByKey(KeyUtil.generateKey(date,
                            businessServiceId));
            if (availableTimeSlotsByDay == null){
                List<TimeSlot> availableTimeSlots = computeTimeSlots(businessService.get());
                cachingAppointmentSchedulerService.saveAvailableTimeSlotsByKey(KeyUtil.generateKey(date,
                        businessServiceId), availableTimeSlots);
            }
            return availableTimeSlotsByDay;
        } else {
            throw new BusinessEntityNotFoundException(BusinessService.ENTITY_NAME, businessServiceId);
        }
    }

    @Override
    public ReservationDto reserve(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.dtoTo(reservationDto);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationDto modifyReservation(Long reservationId, ReservationDto reservationDto) {
        Optional<Reservation> existOptional = reservationRepository.findById(reservationId);
        if (existOptional.isPresent()){
            Reservation newReservation = reservationMapper.dtoTo(reservationDto);
            Reservation existReservation = existOptional.get();
            LocalDateTime bookingTime = newReservation.getBookingTime();
            existReservation.setBookingTime(bookingTime);

            updateTimeSlotsInCache(existReservation.getService().getId(), existReservation.getDuration(),
                    newReservation.getDuration(),
                    bookingTime.toLocalDate());

            Reservation saved = reservationRepository.save(existReservation);
            return reservationMapper.toDto(saved);
        } else {
            throw new BusinessEntityNotFoundException(Reservation.ENTITY_NAME, reservationId);
        }
    }

    private void updateTimeSlotsInCache(Long businessServiceId,
                                        Duration existReservationDuration,
                                        Duration newReservationDuration,
                                        LocalDate date) {
        TimeSlot existTimeSlot = computeTimeSlot(existReservationDuration);
        TimeSlot newTimeSlot = computeTimeSlot(newReservationDuration);
        cachingAppointmentSchedulerService.removeTimeSlotByKey(KeyUtil.generateKey(date,
                businessServiceId), existTimeSlot);
        cachingAppointmentSchedulerService.addTimeSlotByKey(KeyUtil.generateKey(date,
                businessServiceId), newTimeSlot);
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
