package org.booking.core.domain.request;

import lombok.Data;

@Data
public class BusinessHoursRequest {
	private String openTime;
	private String closeTime;
}