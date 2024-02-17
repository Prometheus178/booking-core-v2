package org.booking.core.domain.dto;

import lombok.Data;
import org.booking.core.domain.entity.reservation.Duration;


@Data
public class ReservationDto {

    private Long id;
    private CustomerDto customer;
    private BusinessServiceDto service;
    private EmployeeDto employee;
    private String bookingTime;
    private Duration duration;
    private String state;
    private boolean canceled;

}
