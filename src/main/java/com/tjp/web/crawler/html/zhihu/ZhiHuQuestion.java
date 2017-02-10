/**
 * 
 */
package com.tjp.web.crawler.html.zhihu;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tjp.web.crawler.download.Download;
import com.tjp.web.crawler.inter.HtmlInter;

/**
 * @author TangJP
 * @date 2017年2月10日
 */
public class ZhiHuQuestion implements HtmlInter {
	
	private Document htmlDoc=null;
	private Element head=null;
	private Element body=null;
	private String title="";
	private String url="";
	
	private String savePath = "E:/zhihu_image";
	
	/**
	 * 
	 */
	public ZhiHuQuestion(Document htmlDoc,String url) {
		// TODO Auto-generated constructor stub
		this.htmlDoc=htmlDoc;
		this.url=url;
		if(this.htmlDoc!=null)
		{
			head=this.htmlDoc.head();
			body=this.htmlDoc.body();
			title=this.htmlDoc.title();
		}
	}

	/* (non-Javadoc)
	 * @see com.tjp.web.crawler.inter.HtmlInter#Head()
	 */
	public void Head() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tjp.web.crawler.inter.HtmlInter#Body()
	 */
	public void Body() {
		// TODO Auto-generated method stub
		Elements itemDivs = body.getElementsByClass("List-item");
		System.out.println(itemDivs.size());
		for(Element item : itemDivs)
		{
			//用户信息
			Elements userInfos=item.getElementsByClass("AuthorInfo");
			if(userInfos!=null && userInfos.size()>0)
			{
				Element userInfo=userInfos.first();
				Elements imgs=userInfos.select("img");
				for(Element img : imgs)
				{
					String url=img.attr("srcset");
					String urlValue[]=url.split(" ");
					System.out.println("user "+urlValue[0]);
					Download.getInstance().downloadService(urlValue[0], savePath);
				}
			}
			
			Elements contentList=item.getElementsByClass("ContentItem-content ContentItem-content--unescapable");
			if(contentList!=null && contentList.size()>0)
			{
				Element content=contentList.first();
				Elements imgs=content.select("img");
				for(Element img : imgs)
				{
					String url=img.attr("data-actualsrc");
					System.out.println("cont  "+url);
					Download.getInstance().downloadService(url, savePath);
				}
			}
		}
	}

}
