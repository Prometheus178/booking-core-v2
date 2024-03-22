package org.booking.core.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	public static final String BOOKING_CORE_TOPIC = "booking-core-topic";

	@Bean
	public NewTopic bookingCoreTopic(){
		return TopicBuilder.name(BOOKING_CORE_TOPIC).build();
	}
}
