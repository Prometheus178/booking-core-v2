package org.booking.core.api.v1.customer;


import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.request.ReservationRequest;
import org.booking.core.domain.response.ReservationResponse;
import org.booking.core.service.appointment.AppointmentSchedulerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/customers/appointments", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
		MediaType.APPLICATION_JSON_VALUE)
public class AppointmentApi {

	private final AppointmentSchedulerService appointmentSchedulerService;


	@GetMapping("/find/available-time-slots")
	public ResponseEntity<List<TimeSlot>> findAvailableTimeSlots(@RequestParam("businessServiceId") Long businessServiceId,
																 @RequestParam("day") Long day) {
		List<TimeSlot> availableSlots = appointmentSchedulerService.findAvailableSlots(businessServiceId,
				LocalDate.ofEpochDay(day));
		return ResponseEntity.ok().body(availableSlots);
	}

	@PostMapping("/")
	public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest dto) {
		ReservationResponse reserved = appointmentSchedulerService.reserve(dto);
		return ResponseEntity.ok().body(reserved);

	}

	@PutMapping("/{reservationId}")
	public ResponseEntity<ReservationResponse> update(@PathVariable("reservationId") Long reservationId, @RequestBody ReservationRequest dto) {
		ReservationResponse modifiedReservation = appointmentSchedulerService.modifyReservation(reservationId, dto);
		return ResponseEntity.ok().body(modifiedReservation);
	}

	@DeleteMapping("/{reservationId}")
	public ResponseEntity<Boolean> delete(@PathVariable("reservationId") Long reservationId) {
		return ResponseEntity.ok().body(appointmentSchedulerService.cancelReservation(reservationId));
	}

}
