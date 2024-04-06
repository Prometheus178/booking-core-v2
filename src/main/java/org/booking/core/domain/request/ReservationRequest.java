package org.booking.core.domain.request;

import lombok.Data;


@Data
public class ReservationRequest {

	private Long businessServiceId;
	private String employeeEmail;
	private String bookingTime;
	private DurationRequest duration;

}
