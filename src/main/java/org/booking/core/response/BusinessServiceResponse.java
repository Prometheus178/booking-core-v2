package org.booking.core.response;

import lombok.Data;

@Data
public class BusinessServiceResponse {

	private Long id;
	private Long businessId;
	private String name;
	private String description;
	private double price;
	private int duration;
	private BusinessResponse business;

}
