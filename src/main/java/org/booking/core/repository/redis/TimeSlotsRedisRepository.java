package org.booking.core.repository.redis;

import lombok.extern.java.Log;
import org.booking.core.domain.entity.reservation.TimeSlotList;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Log
@Repository
public class TimeSlotsRedisRepository {

	private final HashOperations hashOperations;

	public TimeSlotsRedisRepository(RedisTemplate redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void create(TimeSlotList timeSlotList) {
		hashOperations.put("TIME_SLOTS", timeSlotList.getKey(), timeSlotList);
		log.info(String.format("TIME_SLOTS with ID %s saved", timeSlotList.getKey()));
	}

	public TimeSlotList get(String key) {
		log.info(String.format("Find TIME_SLOTS with ID %s", key));
		return (TimeSlotList) hashOperations.get("TIME_SLOTS", key);
	}

	public Map<String, TimeSlotList> getAll() {
		return hashOperations.entries("TIME_SLOTS");
	}

	public void update(TimeSlotList timeSlotList) {
		hashOperations.put("TIME_SLOTS", timeSlotList.getKey(), timeSlotList);
		log.info(String.format("TIME_SLOTS with ID %s updated", timeSlotList.getKey()));
	}

	public void delete(String key) {
		hashOperations.delete("TIME_SLOTS", key);
		log.info(String.format("TIME_SLOTS with ID %s deleted", key));
	}


}
