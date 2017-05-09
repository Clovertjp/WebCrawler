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

public class ZhiHuUserQuestion implements HtmlInter {
	
	private Document htmlDoc = null;
	private Element head = null;
	private Element body = null;
	private String title = "";
	private String url = "";

	public ZhiHuUserQuestion(Document htmlDoc, String url) {
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
		
		Elements profileMan=body.getElementsByClass("Card ProfileMain");
		if(profileMan!=null && profileMan.size()>0)
		{
			Elements listItems=profileMan.first().getElementsByClass("List-item");
//			System.out.println(listItems.size());
			for(Element item : listItems)
			{
				Elements titles=item.getElementsByClass("ContentItem-title");
				if(titles==null || titles.size()<=0)
				{
					continue;
				}
				String questionUrl=titles.first().select("a").first().attr("href");
				
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
		}
		

	}

}
