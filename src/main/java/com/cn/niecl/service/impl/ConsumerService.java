package com.cn.niecl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.niecl.service.handler.RedisHandler;

@Service
public class ConsumerService implements RedisHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private RedisLock redisLock;

	@Override
	public String queueName() {
		return "LogA-key";
	}

	@Override
	public String consume(String msgBody) {
		// 加锁，防止消息重复投递
		String lockKey = "lock-order-uuid-A";
		boolean lock = false;
		try {
			lock = redisLock.addLock(lockKey, 60);
			if (!lock) {
				return "success";
			}
			LOG.info("LogA-key == >>" + msgBody);
		} finally {
			if (lock) {
				redisLock.removeLock(lockKey);
			}
		}
		return "success";
	}

}
