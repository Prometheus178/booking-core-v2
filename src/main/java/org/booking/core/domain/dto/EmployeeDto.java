package org.booking.core.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDto {
    private Long id;
    private Set<Long> businessIds;
    private String email;
    private String name;
    private EmployeeReservationHistoryDto reservationHistoryDto;
}
