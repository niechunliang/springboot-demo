package com.cn.niecl;

import java.net.SocketTimeoutException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public final class RedisUtil {

	private static String ADDR = "10.200.10.100";

	// Redis的端口号
	private static int PORT = 6379;

	// 访问密码
	private static String AUTH = "";

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 800;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 100;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static long MAX_WAIT = 50000;

	private static int TIMEOUT = 50000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	public static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		int timeoutCount = 0;
		while (true) // 如果是网络超时则多试几次
		{
			try {
				Jedis jedis = jedisPool.getResource();
				return jedis;
			} catch (Exception e) {
				// 底层原因是SocketTimeoutException，不过redis已经捕捉且抛出JedisConnectionException，不继承于前者
				if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException) {
					timeoutCount++;
					System.out.println("getJedis timeoutCount=" + timeoutCount);
					if (timeoutCount > 3) {
						break;
					}
				} else {

					break;
				}
			}
		}
		return null;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.close();
		}
	}

	public static void mset(String key, String value) {
		Jedis redis = getJedis();
		try {
			redis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisPool.close();
		}

	}

	public static String mget(String co_make, String key) {
		Jedis redis = getJedis();
		String value = "";
		try {

			if (key.equals("")) {
				value = redis.get(co_make);
			} else {
				if (redis.get(co_make) != null) {
//					net.sf.json.JSONObject json1 = net.sf.json.JSONObject.fromObject(redis.get(co_make).toString());
//					if (json1.get(key) != null) {
//						value = json1.getString(key);
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisPool.close();
		}

		return setNullToBanlk(value);
	}

	public static String mget(String key) {
		Jedis redis = getJedis();
		String value = "";
		try {
			value = redis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisPool.close();
		}

		return setNullToBanlk(value);
	}

	public static void mdel(String key) {
		Jedis redis = getJedis();
		try {
			redis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisPool.close();
		}

	}

	public static String setNullToBanlk(String name) {
		if (name == null) {
			name = "";
		}
		return name != null ? name.trim() : "";
	}

	public RedisUtil() {

	}

	public static void main(String[] args) {
		for (int i = 0; i < 300; i++) {
			new Thread(new Runnable() {

				public void run() {
					Jedis redis = getJedis();
					try {
						for (int i = 0; i < 100; i++) {
							redis.mset("a", "1");
							System.out.println("线程1成功===" + i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
		}
	}
}
