package org.booking.core.service.notification;

public interface NotificationDataProvider<T , R> {


	R generateMessage(String action, T obj);

}
