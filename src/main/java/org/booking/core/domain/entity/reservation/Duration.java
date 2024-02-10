package org.booking.core.domain.entity.reservation;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Duration {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
