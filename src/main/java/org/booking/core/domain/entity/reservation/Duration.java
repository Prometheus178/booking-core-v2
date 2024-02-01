package org.booking.core.domain.entity.reservation;

import lombok.ToString;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@ToString
@Embeddable
public class Duration {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
