package com.tjp.web.crawler.task;

import org.jsoup.nodes.Document;

import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.html.zhihu.ZhiHuExplore;
import com.tjp.web.crawler.html.zhihu.ZhiHuQuestion;
import com.tjp.web.crawler.html.zhihu.ZhiHuUser;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserFollow;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserQuestion;

public class HtmlTask implements Runnable {
	
	private String key;
	private String url;
	private Document htmlDoc;
	
	public HtmlTask(String key,String url,Document htmlDoc)
	{
		this.key=key;
		this.url=url;
		this.htmlDoc=htmlDoc;
	}

	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println(key+"+++++++++++"+url);
		
		if(Contants.KEY_EXPLORE.equals(key))
		{
			new ZhiHuExplore(htmlDoc, url).Body();
		}else if(Contants.KEY_QUESTION.equals(key))
		{
			new ZhiHuQuestion(htmlDoc, url).Body();
		}else if(Contants.KEY_USER.equals(key))
		{
			new ZhiHuUser(htmlDoc, url).Body();
		}else if(Contants.KEY_USER_FOLLOW.equals(key))
		{
			new ZhiHuUserFollow(htmlDoc, url).Body();
		}else if(Contants.KEY_USER_QUESTION.equals(key))
		{
			new ZhiHuUserQuestion(htmlDoc, url).Body();
		}
	}

}
