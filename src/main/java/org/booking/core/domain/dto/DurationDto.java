package org.booking.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class DurationDto {

    private String startTime;
    private String endTime;
}
