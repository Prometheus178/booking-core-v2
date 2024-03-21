package org.booking.core.domain.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserReservationHistoryRequest {
	private Long id;
	private Set<ReservationRequest> reservations;
	private Long customerId;
	private String eventType;
	private String eventTime;
	private String details;

}