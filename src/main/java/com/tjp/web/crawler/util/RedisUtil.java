package com.tjp.web.crawler.util;

import java.util.ArrayList;
import java.util.List;

import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.inter.RedisBatch;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static RedisUtil redis = new RedisUtil();

	private JedisPool pool = null;

	private RedisUtil() {
		

	}
	
	public void init()
	{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(Config.REDIS_MAXIDEL);
			config.setMaxWaitMillis(Config.REDIS_MAXWAITMILLIS);
			config.setTestOnBorrow(true);
			pool = new JedisPool(Config.REDIS_IP, Config.REDIS_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RedisUtil getInstance() {
		return redis;
	}

	private JedisPool getPool() {
		return pool;
	}

	private Jedis getJedis() {
		return getPool().getResource();
	}

	public String lPop(String key) {
		Jedis jedis = getJedis();
		String value = "";
		try {
			value = jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	public String rPop(String key) {
		Jedis jedis = getJedis();
		String value = "";
		try {
			value = jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	public String get(String key) {
		Jedis jedis = getJedis();
		String value = "";
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}
	
	public void hset(String key,String field,String value) {
		Jedis jedis = getJedis();
		try {
			System.out.println("hset "+value);
			jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	

	public void lRemList(String key, List<String> list) {
		if (list == null || list.size() <= 0) {
			return;
		}
		Jedis jedis = getJedis();
		try {
			for (String value : list) {
				jedis.lrem(key, 1, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void lPush(String key, String value) {
		Jedis jedis = getJedis();
		try {
			System.out.println("lpush "+value);
			jedis.lpush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void set(String key, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public List<String> lRange(String key, int begin, int end) {
		Jedis jedis = getJedis();
		List<String> list = new ArrayList<String>();
		try {
			list = jedis.lrange(key, begin, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return list;
	}
	
	public Object batch(RedisBatch batch){
        Jedis jedis = getJedis();
        try {
            return batch.run(jedis);
        } finally {
            jedis.close();
        }
    }
	
	public static void main(String[] args) {
		Config.load();
		getInstance().init();
		System.out.println(Config.REDIS_URL_KEY);
		System.out.println(getInstance().lRange(Config.REDIS_URL_KEY,0,-1).size());
	}
	
}
