package org.booking.core.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerReservationHistoryDto {
    private Long id;
    private Set<ReservationDto> reservations;
    private CustomerDto customer;
    private String eventType;
    private String eventTime;
    private String details;

}