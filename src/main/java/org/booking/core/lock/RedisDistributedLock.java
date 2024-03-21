package org.booking.core.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class RedisDistributedLock {

	private final RedissonClient redisson;

	public RedisDistributedLock(RedissonClient redisson) {
		this.redisson = redisson;
	}

	public RLock getLock(String name) {
		return redisson.getLock(name);
	}


}
