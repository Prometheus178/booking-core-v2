package org.booking.core.domain.dto;

import lombok.Data;
import org.booking.core.domain.entity.business.BusinessHours;
import org.booking.core.domain.entity.business.ReservationSchedule;
import org.booking.core.domain.entity.business.service.BusinessService;

import java.util.HashSet;
import java.util.Set;

@Data
public class BusinessDto {
    private Long id;
    private String type;
    private String name;
    private String address;
    private String description;
    private BusinessHoursDto businessHours;
    private Set<BusinessServiceDto> businessServices;
    private ReservationScheduleDto reservationSchedule;

}
