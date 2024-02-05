package org.booking.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReservationDto {

    private long customer;

    private long service;

    private long employee;

    private LocalDateTime bookingTime;

    private boolean canceled;

}
