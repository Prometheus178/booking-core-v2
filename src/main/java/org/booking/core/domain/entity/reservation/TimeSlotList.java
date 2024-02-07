package org.booking.core.domain.entity.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TimeSlotList implements Serializable {

    private static final long serialVersionUID = 1L;
    String key;
    List<TimeSlot> timeSlots;
}
