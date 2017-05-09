package com.tjp.web.crawler.html.zhihu;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.util.RedisUtil;

public class ZhiHuExplore implements HtmlInter  {
	
	private Document htmlDoc = null;
	private Element head = null;
	private Element body = null;
	private String title = "";
	private String url = "";
	
	public ZhiHuExplore(Document htmlDoc, String url) {
		// TODO Auto-generated constructor stub
		this.htmlDoc = htmlDoc;
		this.url = url;
		if (this.htmlDoc != null) {
			head = this.htmlDoc.head();
			body = this.htmlDoc.body();
			title = this.htmlDoc.title();
		}

	}

	public void Head() {
		// TODO Auto-generated method stub
		
	}

	public void Body() {
		// TODO Auto-generated method stub
		if(body==null)
		{
			return;
		}
		Elements items=body.getElementsByClass("explore-feed feed-item");
		for(Element item: items)
		{
			Elements question=item.getElementsByClass("question_link");
			if(question!=null && question.size()>0)
			{
				String questionUrl=question.get(0).attr("href");//获取问题链接
				if(!Strings.isNullOrEmpty(questionUrl))
				{
					List<String> urls=Splitter.on("/").splitToList(questionUrl);
					if(urls.size()>=5)
					{
						questionUrl="/"+urls.get(1)+"/"+urls.get(2);
					}
					
					RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_QUESTION+Config.WEB_URL+questionUrl);
				}
			}
			
			Elements user=item.getElementsByClass("author-link");
			
			if(user!=null && user.size()>0)
			{
				String userUrl=user.get(0).attr("href");//获取问题链接
				if(!Strings.isNullOrEmpty(userUrl))
				{
					RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER+Config.WEB_URL+userUrl);
				}
			}
		}
	}

}
