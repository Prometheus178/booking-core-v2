package org.booking.core.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Log
@RequiredArgsConstructor
@Service
public class NotificationServiceBean implements NotificationService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void sent(String topicName, String json) {
		kafkaTemplate.send(topicName, json);
		log.info(String.format("send message to topic %s", topicName));
	}

}
