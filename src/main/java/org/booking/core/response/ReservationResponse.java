package org.booking.core.response;

import lombok.Data;


@Data
public class ReservationResponse {

	private Long id;
	private Long businessServiceId;
	private String employeeEmail;
	private String bookingTime;
	private DurationResponse duration;
	private boolean canceled;

}
