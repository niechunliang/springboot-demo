package com.cn.niecl.service;

public interface RedisService {
	void saveQueue(String queueKey, String msgBody);
}
