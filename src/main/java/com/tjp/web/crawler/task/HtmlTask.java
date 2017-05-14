package com.tjp.web.crawler.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.html.zhihu.ZhiHuExplore;
import com.tjp.web.crawler.html.zhihu.ZhiHuQuestion;
import com.tjp.web.crawler.html.zhihu.ZhiHuUser;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserFollow;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserQuestion;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.json.zhihu.ZhiHuQuestionJson;

public class HtmlTask implements Runnable {
	
	private String key;
	private String url;
	private String htmlData;
	
	public HtmlTask(String key,String url,String htmlData)
	{
		this.key=key;
		this.url=url;
		this.htmlData=htmlData;
	}

	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println(key+"+++++++++++"+url);
		
		HtmlInter html=null;	
		if(Contants.KEY_EXPLORE.equals(key))
		{
			Document htmlDoc=Jsoup.parse(htmlData);
			html=new ZhiHuExplore(htmlDoc, url);
		}else if(Contants.KEY_QUESTION.equals(key))
		{
			Document htmlDoc=Jsoup.parse(htmlData);
			html=new ZhiHuQuestion(htmlDoc, url);
		}else if(Contants.KEY_USER.equals(key))
		{
			Document htmlDoc=Jsoup.parse(htmlData);
			html=new ZhiHuUser(htmlDoc, url);
		}else if(Contants.KEY_USER_FOLLOW.equals(key))
		{
			Document htmlDoc=Jsoup.parse(htmlData);
			html=new ZhiHuUserFollow(htmlDoc, url);
		}else if(Contants.KEY_USER_QUESTION.equals(key))
		{
			Document htmlDoc=Jsoup.parse(htmlData);
			html=new ZhiHuUserQuestion(htmlDoc, url);
		}else if(Contants.KEY_QUESTION_JSON.equals(key))
		{
//			System.out.println("11111"+htmlData);
			html=new ZhiHuQuestionJson(htmlData, url);
		}
		
		if(html!=null)
		{
			html.Body();
		}
	}

}
