package org.booking.core.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.reservation.Duration;
import org.booking.core.domain.entity.reservation.State;

import java.time.LocalDateTime;


@Data
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
