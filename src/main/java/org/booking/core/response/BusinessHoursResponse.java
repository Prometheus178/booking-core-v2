package org.booking.core.response;

import lombok.Data;

@Data
public class BusinessHoursResponse {
	private Long id;
	private String openTime;
	private String closeTime;
}