package org.booking.core.service.appointment.cache;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.entity.reservation.TimeSlotList;
import org.booking.core.repository.redis.TimeSlotsRedisRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CachingAppointmentSchedulerServiceBean implements CachingAppointmentSchedulerService{

    private final TimeSlotsRedisRepository timeSlotsRedisRepository;


    @Override
    public List<TimeSlot> findAvailableTimeSlotsByKey(String key) {
        TimeSlotList timeSlotList = timeSlotsRedisRepository.get(key);
        if (timeSlotList != null) {
            return timeSlotList.getTimeSlots();
        }
        return Collections.emptyList();
    }

    @Override
    public void saveAvailableTimeSlotsByKey(String key, List<TimeSlot> availableTimeSlots) {
        timeSlotsRedisRepository.create(new TimeSlotList(key, availableTimeSlots));
    }

    @Override
    public void removeTimeSlotByKey(String key, TimeSlot existTimeSlot) {
        TimeSlotList timeSlotList = timeSlotsRedisRepository.get(key);
        List<TimeSlot> timeSlots = timeSlotList.getTimeSlots();
        timeSlots.remove(existTimeSlot);
        timeSlotsRedisRepository.update(timeSlotList);
    }

    @Override
    public void addTimeSlotByKey(String key, TimeSlot newTimeSlot) {
        TimeSlotList timeSlotList = timeSlotsRedisRepository.get(key);
        List<TimeSlot> timeSlots = timeSlotList.getTimeSlots();
        timeSlots.add(newTimeSlot);
        timeSlotsRedisRepository.update(timeSlotList);
    }
}

