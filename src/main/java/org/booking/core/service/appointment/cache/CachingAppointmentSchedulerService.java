package org.booking.core.service.appointment.cache;

import org.booking.core.domain.entity.reservation.TimeSlot;

import java.util.List;

public interface CachingAppointmentSchedulerService {

	List<TimeSlot> findAvailableTimeSlotsByKey(String key);

	void saveAvailableTimeSlotsByKey(String key, List<TimeSlot> availableTimeSlots);

	void removeTimeSlotByKey(String key, TimeSlot existTimeSlot);

	void addTimeSlotByKey(String key, TimeSlot newTimeSlot);
}

