package org.booking.core.service.notification;

public interface NotificationService {

	void sent(String topicName, String json);

}
