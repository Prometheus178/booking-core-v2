package org.booking.core.request;

import lombok.Data;

@Data
public class BusinessHoursRequest {
	private String openTime;
	private String closeTime;
}