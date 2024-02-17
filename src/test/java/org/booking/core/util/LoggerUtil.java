package org.booking.core.util;



public class LoggerUtil {
    private LoggerUtil() {
    }

    public static void logInfo(LogActionType actionType, String entityName, Object entityId) {
        String logMessage = String.format("%s %s with ID: %s", actionType.toString(),
                entityName, entityId);
        System.out.println(logMessage);
    }
}
