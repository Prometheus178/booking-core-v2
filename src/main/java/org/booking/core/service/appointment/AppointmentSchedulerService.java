package org.booking.core.service.appointment;

import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.reservation.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSchedulerService {

    List<TimeSlot> findAvailableSlots(Long businessId, Long businessServiceId, LocalDate day);

    ReservationDto reserve(ReservationDto reservationDto);

    ReservationDto modifyReservation(Long reservationId, ReservationDto reservationDto);

    boolean cancelReservation(Long reservationId);

}
