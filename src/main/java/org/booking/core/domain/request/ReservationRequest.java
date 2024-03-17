package org.booking.core.domain.request;

import lombok.Data;


@Data
public class ReservationRequest {

    private Long serviceId;
    private Long employeeId;
    private String bookingTime;
	private DurationRequest duration;

}
