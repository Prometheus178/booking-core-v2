package org.booking.core.domain.dto;

import lombok.Data;


@Data
public class ReservationDto {

    private Long id;
    private CustomerDto customer;
    private BusinessServiceDto service;
    private EmployeeDto employee;
    private String bookingTime;
    private DurationDto DurationDto;
    private String state;
    private boolean canceled;

}
