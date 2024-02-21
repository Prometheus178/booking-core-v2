package org.booking.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
public class DurationDto {

    private String startTime;
    private String endTime;
}
