package org.booking.core.domain.entity.reservation;

import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Date;

@ToString
@Embeddable
public class Duration {

    private Date startTime;
    private Date endTime;
}
