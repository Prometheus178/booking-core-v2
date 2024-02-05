package org.booking.core.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class KeyUtil {

    public static String generateKey(LocalDate date, Long businessServiceId){
        return date.toString() + "|" + businessServiceId;
    }
}
