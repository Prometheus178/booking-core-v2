package org.booking.core.domain.response;

import lombok.Data;


@Data
public class ReservationResponse {

	private Long id;
	private Long businessServiceId;
	private Long employeeEmail;
	private String bookingTime;
	private DurationResponse duration;
	private boolean canceled;

}
