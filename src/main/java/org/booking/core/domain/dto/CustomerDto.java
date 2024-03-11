package org.booking.core.domain.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String email;
    private String name;
    private UserReservationHistoryDto reservationHistory;
}
