package com.tjp.web.crawler.task;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tjp.web.crawler.manager.BloomFilterManager;
import com.tjp.web.crawler.manager.ScheduleManager;
import com.tjp.web.crawler.util.HttpUtil;

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
			String html=HttpUtil.http(url, null, HttpUtil.RequestMethod.GET, new HashMap<String, String>());
			Document htmlDoc=Jsoup.parse(html);
			ScheduleManager.getInstance().getHtmlScheduler().execute(new HtmlTask(key, url, htmlDoc));
		}finally
		{
			ScheduleManager.getInstance().desHttpCount();
		}
		
	}

}
