package org.booking.core.service.appointment.cache;

import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.entity.reservation.TimeSlotList;
import org.booking.core.repository.redis.TimeSlotsRedisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CachingAppointmentSchedulerServiceBean implements CachingAppointmentSchedulerService{

    private final TimeSlotsRedisRepository timeSlotsRedisRepository;

    public CachingAppointmentSchedulerServiceBean(TimeSlotsRedisRepository timeSlotsRedisRepository) {
        this.timeSlotsRedisRepository = timeSlotsRedisRepository;
    }

    @Override
    public List<TimeSlot> findAvailableTimeSlotsByKey(String key) {
        TimeSlotList timeSlotList = timeSlotsRedisRepository.get(key);
        if (timeSlotList != null) {
            return timeSlotList.getTimeSlots();
        }
        return null;
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

