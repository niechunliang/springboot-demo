package com.cn.niecl.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.cn.niecl.common.redis.JedisUtil;
import com.cn.niecl.service.handler.RedisHandler;
import com.cn.niecl.util.SpringContextUtil;

import redis.clients.jedis.Jedis;

public class RedisListener implements InitializingBean {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisListener.class);

	private List<RedisHandler> handlers = null;
	private ExecutorService product = null;
	private ExecutorService consumer = null;
	private JedisUtil jedisUtil = null;

	/**
	 * 初始化配置
	 */
	//@PostConstruct--->InitializingBean等同于
	@Override
	public void afterPropertiesSet() {
		
		jedisUtil = SpringContextUtil.getBean(JedisUtil.class);
		handlers = SpringContextUtil.getBeans(RedisHandler.class);
		product = new ThreadPoolExecutor(10, 15, 60 * 3, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		consumer = new ThreadPoolExecutor(10, 15, 60 * 3, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		for (final RedisHandler redisHandler : handlers) {
			product.execute(new Runnable() {
				@Override
				public void run() {
					redisTask(redisHandler);
				}
			});
		}
	}

	/**
	 * 队列监听
	 */
	public void redisTask(final RedisHandler redisHandler) {
		while (true) {
			Jedis jedis = jedisUtil.getJedis();
			final List<String> msgBodyList = jedis.brpop(0, redisHandler.queueName());
			if (CollectionUtils.isNotEmpty(msgBodyList)) {
				LOG.info("获取到消息。。");
				consumer.execute(new Runnable() {
					@Override
					public void run() {
						redisHandler.consume(msgBodyList.get(1));
					}
				});
			}
		}
	}
}
