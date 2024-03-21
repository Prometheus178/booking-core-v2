package org.booking.core.util;

import lombok.experimental.UtilityClass;
import org.booking.core.domain.entity.reservation.TimeSlot;

import java.time.LocalDate;

@UtilityClass
public class KeyUtil {

	public static String generateKey(LocalDate date, Long businessServiceId) {
		return date.toString() + "|" + businessServiceId;
	}

	public static String generateKey(LocalDate date, Long businessServiceId, TimeSlot timeSlot) {
		return date.toString() + "|" + businessServiceId + "|" + timeSlot.toString();
	}
}
