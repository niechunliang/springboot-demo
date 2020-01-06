package com.cn.niecl.service.Listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.cn.niecl.service.MessageReceiver;
import com.cn.niecl.util.SpringContextUtil;

import redis.clients.jedis.JedisPoolConfig;
/**
 * redis订阅发布
 * 
 * @author niecl
 *
 */
//先加载SpringContextUtil
@DependsOn(value = "springContextUtil")
@Configuration // 相当于xml中的beans
public class RedisMsgListener {

	@Value("${redis.hostname}")
	private String hostname;

	@Value("${redis.port}")
	private int port;

	/**
	 * redis消息监听器容器 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * 
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
	@Bean // 相当于xml中的bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 订阅了一个叫chat 的通道
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
		// 订阅了一个叫chatA 的通道
		container.addMessageListener(listenerAdapter, new PatternTopic("chatA"));
		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
	 * 
	 * @param receiver
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		//给messageListenerAdapter传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	/** redis 读取内容的template */
	@Bean(name = "myStringRedisTemplate")
	StringRedisTemplate template(JedisConnectionFactory connectionFactory) {
		// JedisPoolConfig poolConfig = (JedisPoolConfig)
		// SpringContextUtil.getBean("jedisPoolConfig");
		JedisPoolConfig poolConfig = SpringContextUtil.getBean(JedisPoolConfig.class);

		// Stringboot2.0以上
		// JedisClientConfiguration clientConfig =
		// JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig)
		// .and().readTimeout(Duration.ofMillis(redisTimeout)).build();
		// 单点redis
		// RedisStandaloneConfiguration redisConfig = new
		// RedisStandaloneConfiguration();
		// 哨兵redis
		// RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
		// 集群redis
		// RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
		connectionFactory.setPoolConfig(poolConfig);
		connectionFactory.setHostName(hostname);
		connectionFactory.setPort(port);
		connectionFactory.setUsePool(true);
		return new StringRedisTemplate(connectionFactory);
	}
}
