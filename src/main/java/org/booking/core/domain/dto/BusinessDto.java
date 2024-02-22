package org.booking.core.domain.dto;

import lombok.Data;

@Data
public class BusinessDto {
    private Long id;
    private String description;
    private String address;
    private String name;
    private String type;
}
