package org.booking.core.domain.entity.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TimeSlotList implements Serializable {

	private static final long serialVersionUID = 1L;

	String key;
	List<TimeSlot> timeSlots;
}
