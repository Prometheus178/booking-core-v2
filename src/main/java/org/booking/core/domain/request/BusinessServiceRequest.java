package org.booking.core.domain.request;

import lombok.Data;

@Data
public class BusinessServiceRequest {

	private Long businessId;
	private String name;
	private String description;
	private double price;
	private int duration;

}
