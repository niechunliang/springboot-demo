package com.cn.niecl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.niecl.common.redis.JedisUtil;

@Service
public class RedisLock {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisLock.class);

	@Autowired
	private JedisUtil jedisUtil;

	private static String keyPrefix = "RedisLock:";

	public boolean addLock(String key, long expire) {
		LOG.info("添加锁");
		String value = jedisUtil.getJedis().set(keyPrefix + key, "1", "nx", "ex", expire);
		return value != null;
	}

	public void removeLock(String key) {
		LOG.info("删除锁");
		jedisUtil.getJedis().del(keyPrefix + key);
	}
}
