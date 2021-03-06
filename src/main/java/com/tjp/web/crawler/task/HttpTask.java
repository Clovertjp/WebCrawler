package com.tjp.web.crawler.task;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tjp.web.crawler.manager.BloomFilterManager;
import com.tjp.web.crawler.manager.ScheduleManager;
import com.tjp.web.crawler.util.HttpUtil;
import com.tjp.web.crawler.util.zhihu.ZhiHuHttpUtil;

public class HttpTask implements Runnable {
	private String key;
	private String url;
	
	public HttpTask(String key,String url)
	{
		this.key=key;
		this.url=url;
	}

	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println(key+"-------"+url);
			if(BloomFilterManager.getInstance().containsWithAdd(url))
			{
				return;
			}
			String html=new ZhiHuHttpUtil().http(key,url, null, HttpUtil.RequestMethod.GET, new HashMap<String, String>());
			ScheduleManager.getInstance().getHtmlScheduler().execute(new HtmlTask(key, url, html));
		}finally
		{
			ScheduleManager.getInstance().desHttpCount();
		}
		
	}

}
