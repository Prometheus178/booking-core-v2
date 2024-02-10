package org.booking.core.domain.dto;

import lombok.Data;

@Data
public class BusinessServiceDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Long businessId;

}
