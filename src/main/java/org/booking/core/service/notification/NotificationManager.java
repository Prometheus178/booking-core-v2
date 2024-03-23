package org.booking.core.service.notification;

import com.google.gson.Gson;

public interface NotificationManager <T>{

	void sendNotification(String receiver,String action, T obj);

	default String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}
}
