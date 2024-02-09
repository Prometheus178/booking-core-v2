package org.booking.core.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class ReservationScheduleDto {

    private Long id;
    private BusinessDto business;
    private Map<LocalDate, Set<ReservationDto>> reservations;

}
