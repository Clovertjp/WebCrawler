package com.tjp.web.crawler.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
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
	public static String URL_FIRST;
	
	public static List<String> URL_KEYS=new ArrayList<String>();
	public static Map<String,String> URL_PARAM_MAP=new HashMap<String,String>();

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
		URL_FIRST=PropertiesUtil.getInstance().getProperty("web.first.url");
		
		String urlKeys=PropertiesUtil.getInstance().getProperty("html.get.url.key");
		if(!Strings.isNullOrEmpty(urlKeys))
		{
			URL_KEYS=Splitter.on("|").splitToList(urlKeys);
		}
		
		String urlParam=PropertiesUtil.getInstance().getProperty("html.get.param");
		if(!Strings.isNullOrEmpty(urlParam))
		{
			List<String> params=Splitter.on("|").splitToList(urlKeys);
			for(String param :params)
			{
				List<String> tmp=Splitter.on(";").splitToList(urlKeys);
				URL_PARAM_MAP.put(tmp.get(0), tmp.get(1));
			}
		}
		
	}

}
