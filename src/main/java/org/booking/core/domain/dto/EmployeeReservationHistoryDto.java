package org.booking.core.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeReservationHistoryDto {

    private Long id;
    private Set<ReservationDto> reservations;
    private EmployeeDto employee;
    private String eventType;
    private String eventTime;
    private String details;

}