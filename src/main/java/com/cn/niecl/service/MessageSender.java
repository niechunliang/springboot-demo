package com.cn.niecl.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cn.niecl.util.SpringContextUtil;

@EnableScheduling // 开启定时器功能
@Component
public class MessageSender {

	@Scheduled(fixedRate = 20000) // 间隔20s 通过StringRedisTemplate对象向redis消息队列chat频道发布消息
	public void sendMessage() {
		RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) SpringContextUtil
				.getBean("jedisTemplate");
		redisTemplate.convertAndSend("chat", String.valueOf(Math.random()));
		RedisTemplate<String, String> myStringRedisTemplate = (RedisTemplate<String, String>) SpringContextUtil
				.getBean("myStringRedisTemplate");
		myStringRedisTemplate.convertAndSend("chatA", String.valueOf(Math.random()));
	}
}