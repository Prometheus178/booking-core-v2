package org.booking.core.domain.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String email;
    private String name;
    private EmployeeReservationHistoryDto reservationHistory;
}
