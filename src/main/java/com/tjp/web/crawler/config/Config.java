package com.tjp.web.crawler.config;

import com.tjp.web.crawler.util.PropertiesUtil;

public final class Config {

	// redis
	public static String REDIS_IP;
	public static int REDIS_PORT;
	public static int REDIS_MAXIDEL;
	public static int REDIS_MAXWAITMILLIS;
	
	// mysql
	public static boolean AUTO_CLOSE_LEAK_SESSION;
	
	public static String IMAGE_PATH;
	
	public static String REDIS_URL_KEY;
	public static String WEB_URL;
	public static String REDIS_DOWN_URL_KEY;
	
	//thread pool
	public static int HTTP_POOL;
	public static int HTML_POOL;
	public static int DOWN_POOL;
	
	public static String REDIS_HASH_KEY;
	
	public static String HOST;
	public static String COOKIE;
	public static String AUTHOR;
	public static String AGENT;
	public static String HTML_GET_PARAM;
	public static String URL_FIRST;

	public static void load(){
		// redis
		REDIS_IP = PropertiesUtil.getInstance().getProperty("redis.config.ip","127.0.0.1");
		REDIS_PORT = Integer.parseInt(PropertiesUtil.getInstance().getProperty("redis.config.port", "6379"));
		REDIS_MAXIDEL = Integer.parseInt(PropertiesUtil.getInstance().getProperty("redis.config.maxIdle", "50"));
		REDIS_MAXWAITMILLIS = Integer
				.parseInt(PropertiesUtil.getInstance().getProperty("redis.config.maxWaitMillis", "100000"));
		AUTO_CLOSE_LEAK_SESSION=Boolean.parseBoolean(
				PropertiesUtil.getInstance().getProperty("mysql.autoCloseLeakSession","true"));
		
		IMAGE_PATH=PropertiesUtil.getInstance().getProperty("path.image");
		
		REDIS_URL_KEY = PropertiesUtil.getInstance().getProperty("redis.key.url");
		WEB_URL = PropertiesUtil.getInstance().getProperty("web.info.url");
		
		HTTP_POOL = Integer.parseInt(PropertiesUtil.getInstance().getProperty("pool.http", "5"));
		HTML_POOL = Integer.parseInt(PropertiesUtil.getInstance().getProperty("pool.html", "3"));
		DOWN_POOL = Integer.parseInt(PropertiesUtil.getInstance().getProperty("pool.html", "5"));
		
		REDIS_HASH_KEY= PropertiesUtil.getInstance().getProperty("redis.key.hash");
		
		REDIS_DOWN_URL_KEY= PropertiesUtil.getInstance().getProperty("redis.key.down");
		
		HOST=PropertiesUtil.getInstance().getProperty("html.host");
		COOKIE=PropertiesUtil.getInstance().getProperty("html.cookie");
		AUTHOR=PropertiesUtil.getInstance().getProperty("html.authorization");
		AGENT=PropertiesUtil.getInstance().getProperty("html.agent");
		HTML_GET_PARAM=PropertiesUtil.getInstance().getProperty("html.get.param");
		URL_FIRST=PropertiesUtil.getInstance().getProperty("web.first.url");
	}

}
