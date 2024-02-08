package org.booking.core.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CustomerReservationHistoryDto {
    private Long id;
    private Set<ReservationDto> reservations;
    private CustomerDto customer;
    private String eventType;
    private LocalDateTime eventTime;
    private String details;

}