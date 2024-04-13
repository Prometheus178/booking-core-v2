package org.booking.core.response;

import lombok.Data;

import java.util.Set;

@Data
public class BusinessResponse {
	private Long id;
	private String type;
	private String name;
	private String address;
	private String description;
	private BusinessHoursResponse businessHours;
	private Set<BusinessServiceResponse> businessServices;
	private ReservationScheduleResponse reservationSchedule;
	private Set<String> employees;

}
