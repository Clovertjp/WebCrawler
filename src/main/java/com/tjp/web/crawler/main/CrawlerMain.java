package com.tjp.web.crawler.main;

import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.manager.BloomFilterManager;
import com.tjp.web.crawler.manager.ScheduleManager;
import com.tjp.web.crawler.util.RedisUtil;

public class CrawlerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("init config");
		Config.load();
		System.out.println("init redis");
		RedisUtil.getInstance().init();
		System.out.println("init bloom");
		BloomFilterManager.getInstance().init();
		System.out.println("schedule start");
		ScheduleManager.getInstance().start();
	}

}
