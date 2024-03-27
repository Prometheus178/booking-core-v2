package org.booking.core.service.appointment.cache;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.entity.reservation.TimeSlotList;
import org.booking.core.repository.redis.TimeSlotsRedisCaching;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CachingAppointmentSchedulerServiceBean implements CachingAppointmentSchedulerService {

	private final TimeSlotsRedisCaching timeSlotsRedisCaching;

	@Override
	public List<TimeSlot> findAvailableTimeSlotsByKey(String key) {
		TimeSlotList timeSlotList = timeSlotsRedisCaching.get(key);
		if (timeSlotList != null) {
			return timeSlotList.getTimeSlots();
		}
		return Collections.emptyList();
	}

	@Override
	public void saveAvailableTimeSlotsByKey(String key, List<TimeSlot> availableTimeSlots) {
		timeSlotsRedisCaching.create(new TimeSlotList(key, availableTimeSlots));
	}

	@Override
	public void removeTimeSlotByKey(String key, TimeSlot existTimeSlot) {
		TimeSlotList timeSlotList = timeSlotsRedisCaching.get(key);
		List<TimeSlot> timeSlots = timeSlotList.getTimeSlots();
		timeSlots.remove(existTimeSlot);
		timeSlotsRedisCaching.update(timeSlotList);
	}

	@Override
	public void addTimeSlotByKey(String key, TimeSlot newTimeSlot) {
		TimeSlotList timeSlotList = timeSlotsRedisCaching.get(key);
		List<TimeSlot> timeSlots = timeSlotList.getTimeSlots();
		timeSlots.add(newTimeSlot);
		timeSlotsRedisCaching.update(timeSlotList);
	}
}

