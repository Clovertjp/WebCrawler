/**
 * 
 */
package com.tjp.web.crawler.html.zhihu;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.inter.HtmlInter;

/**
 * @author TangJP
 * @date 2017年2月9日
 */
public class ZhiHuIndex implements HtmlInter {

	private Document htmlDoc = null;
	private Element head = null;
	private Element body = null;
	private String title = "";
	private String url = "";

	private String savePath = Config.IMAGE_PATH;

	/**
	 * 
	 */
	public ZhiHuIndex(Document htmlDoc, String url) {
		// TODO Auto-generated constructor stub
		this.htmlDoc = htmlDoc;
		this.url = url;
		if (this.htmlDoc != null) {
			head = this.htmlDoc.head();
			body = this.htmlDoc.body();
			title = this.htmlDoc.title();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tjp.web.crawler.inter.HtmlInter#Head()
	 */
	public void Head() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tjp.web.crawler.inter.HtmlInter#Body()
	 */
	public void Body() {
		// TODO Auto-generated method stub
		if(body==null)
		{
			return;
		}
		Elements divs = body.getElementsByClass("feed-item-inner");
		for (Element div : divs) {
			Elements as = div.getElementsByClass("feed-title");
			for (Element a : as) {
				Elements nodeA = a.select("a");
				if (nodeA == null || nodeA.size() <= 0) {
					continue;
				}
				Element node = nodeA.first();
				System.out.println(node.attr("href") + "    " + node.text());
			}

			Elements avatars = div.getElementsByClass("avatar");
			for (Element ava : avatars) {
				String url = ava.select("img").first().attr("src");
			}

			Elements imgs = div.getElementsByClass("zh-summary summary clearfix");
			for (Element img : imgs) {
				Elements i = img.select("img");
				if (i == null || i.size() <= 0) {
					continue;
				}
				String url = i.first().attr("data-original");
			}

			Elements content = div.getElementsByClass("post-content");
			for (Element con : content) {
				Elements el = con.select("div").first().select("img");
				if (el == null || el.size() <= 0) {
					continue;
				}
				String url = el.first().attr("data-original");
			}
		}
	}

}
