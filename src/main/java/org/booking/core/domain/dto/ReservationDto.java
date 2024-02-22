package org.booking.core.domain.dto;

import lombok.Data;


@Data
public class ReservationDto {

    private Long id;
    private Long customerId;
    private Long serviceId;
    private Long employeeId;
    private String bookingTime;
    private DurationDto duration;
    private boolean canceled;

}
