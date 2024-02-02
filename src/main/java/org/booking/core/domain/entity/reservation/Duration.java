package org.booking.core.domain.entity.reservation;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Duration {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
