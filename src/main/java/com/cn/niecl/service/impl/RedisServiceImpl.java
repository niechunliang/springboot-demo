package com.cn.niecl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.niecl.common.redis.JedisUtil;
import com.cn.niecl.service.RedisService;

/**
 * 
 * @author niecl
 *
 */
@Service
public class RedisServiceImpl implements RedisService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);


	@Autowired
	private JedisUtil.Lists listsUtil;

	@Override
	public void saveQueue(String queueKey, String msgBody) {
		listsUtil.lpush(queueKey, msgBody);
		LOG.info("消息推送成功");
	}
}
