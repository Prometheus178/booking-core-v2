package org.booking.core.service.appointment.cache;

import org.booking.core.domain.entity.reservation.TimeSlot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CachingAppointmentSchedulerServiceBean implements CachingAppointmentSchedulerService{



    @Override
    public List<TimeSlot> findAvailableTimeSlotsByKey(String key) {
        return null;
    }

    @Override
    public void saveAvailableTimeSlotsByKey(String key, List<TimeSlot> availableTimeSlots) {

    }

    @Override
    public void removeTimeSlotByKey(String key, TimeSlot existTimeSlot) {

    }

    @Override
    public void addTimeSlotByKey(String key, TimeSlot newTimeSlot) {

    }
}

