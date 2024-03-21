package org.booking.core.domain.response;

import lombok.Data;

@Data
public class CustomerResponse {

	private Long id;
	private String email;
	private String name;
	private UserReservationHistoryRequest reservationHistory;
}
