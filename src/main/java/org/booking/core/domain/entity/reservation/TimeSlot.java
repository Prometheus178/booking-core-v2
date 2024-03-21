package org.booking.core.domain.entity.reservation;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
public class TimeSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	private final LocalTime startTime;
	private final LocalTime endTime;

	public TimeSlot(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return startTime + "-" + endTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimeSlot timeSlot = (TimeSlot) o;
		return Objects.equals(startTime, timeSlot.startTime) && Objects.equals(endTime, timeSlot.endTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(startTime, endTime);
	}
}
