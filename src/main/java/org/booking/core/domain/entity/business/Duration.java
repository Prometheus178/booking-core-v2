package org.booking.core.domain.entity.business;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Duration {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
