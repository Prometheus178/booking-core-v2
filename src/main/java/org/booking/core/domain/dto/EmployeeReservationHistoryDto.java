package org.booking.core.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class EmployeeReservationHistoryDto {

    private Long id;
    private Set<ReservationDto> reservations;
    private EmployeeDto employee;
    private String eventType;
    private LocalDateTime eventTime;
    private String details;

}