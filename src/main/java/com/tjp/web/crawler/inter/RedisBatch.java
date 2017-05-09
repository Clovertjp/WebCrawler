package com.tjp.web.crawler.inter;

import redis.clients.jedis.Jedis;

public interface RedisBatch {
	public Object run(Jedis jedis);
}
