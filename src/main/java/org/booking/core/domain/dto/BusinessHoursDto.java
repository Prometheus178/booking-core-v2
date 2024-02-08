package org.booking.core.domain.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessHoursDto {
    private Long id;
    private LocalTime openTime;
    private LocalTime closeTime;
}