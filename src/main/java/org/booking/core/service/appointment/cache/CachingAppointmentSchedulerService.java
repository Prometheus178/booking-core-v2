package org.booking.core.service.appointment.cache;

import org.booking.core.service.appointment.TimeSlot;

import java.util.List;

public interface CachingAppointmentSchedulerService {

    List<TimeSlot> findAvailableTimeSlotsByDay(String key);
    void saveAvailableTimeSlotsByDay(String key, List<TimeSlot> availableTimeSlots);
    void removeTimeSlotByDay(String key, TimeSlot existTimeSlot);
    void addTimeSlotByDay(String key, TimeSlot newTimeSlot);
}

