package org.booking.core.service.appointment;

import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.response.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSchedulerService {

	List<TimeSlot> findAvailableSlots(Long businessServiceId, LocalDate day);

	ReservationResponse reserve(ReservationRequest reservationRequest);

	ReservationResponse modifyReservation(Long reservationId, ReservationRequest reservationRequest);

	boolean cancelReservation(Long reservationId);

}
