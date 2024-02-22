package org.booking.core.domain.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessHoursDto {
    private Long id;
    private String openTime;
    private String closeTime;
}