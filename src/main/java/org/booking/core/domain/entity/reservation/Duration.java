package org.booking.core.domain.entity.reservation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Duration {

    private String startTime;
    private String endTime;
}
