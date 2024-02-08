package org.booking.core.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.entity.reservation.State;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReservationDto {

    private Long id;
    private CustomerDto customer;
    private BusinessServiceDto service;
    private EmployeeDto employee;
    private LocalDateTime bookingTime;
    private Duration duration;
    private String state;
    private boolean canceled;

}
