package org.booking.core.api;


import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.service.appointment.AppointmentSchedulerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/appointments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentApi {

    private final AppointmentSchedulerService appointmentSchedulerService;

    public AppointmentApi(AppointmentSchedulerService appointmentSchedulerService) {
        this.appointmentSchedulerService = appointmentSchedulerService;
    }

    @GetMapping("/find/available-time-slots")
    public ResponseEntity<List<TimeSlot>> findAvailableTimeSlots(@RequestParam("businessId") Long businessId,
                                                                 @RequestParam("businessServiceId") Long businessServiceId,
                                                                 @RequestParam("day") Long day) {
        List<TimeSlot> availableSlots = appointmentSchedulerService.findAvailableSlots(businessId,
                businessServiceId,
                LocalDate.ofEpochDay(day));
        return ResponseEntity.ok().body(availableSlots);
    }

    @PostMapping("/")
    public ResponseEntity<ReservationDto> create(@RequestBody ReservationDto dto) {
        ReservationDto reserved = appointmentSchedulerService.reserve(dto);
        return ResponseEntity.ok().body(reserved);

    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> update(@PathVariable("reservationId")Long reservationId, @RequestBody ReservationDto dto) {
        ReservationDto modifiedReservation = appointmentSchedulerService.modifyReservation(reservationId, dto);
        return ResponseEntity.ok().body(modifiedReservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Boolean> delete(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok().body(appointmentSchedulerService.cancelReservation(reservationId));
    }

}
