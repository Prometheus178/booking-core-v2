package org.booking.core.domain.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserReservationHistoryRequest {
	private Long id;
	private Set<ReservationResponse> reservations;
	private Long customerId;
	private String eventType;
	private String eventTime;
	private String details;

}