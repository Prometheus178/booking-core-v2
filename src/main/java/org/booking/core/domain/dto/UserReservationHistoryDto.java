package org.booking.core.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserReservationHistoryDto {
    private Long id;
    private Set<ReservationDto> reservations;
    private Long customerId;
    private String eventType;
    private String eventTime;
    private String details;

}