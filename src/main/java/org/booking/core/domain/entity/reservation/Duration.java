package org.booking.core.domain.entity.reservation;

import javax.persistence.*;

import java.util.Date;

@Embeddable
public class Duration {

    private Date startTime;
    private Date endTime;
}
