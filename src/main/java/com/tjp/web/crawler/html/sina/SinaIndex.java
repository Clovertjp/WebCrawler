package com.tjp.web.crawler.html.sina;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.*;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.util.HttpUtil;

public class SinaIndex implements HtmlInter {

	private Document htmlDoc = null;
	private String head = "<html><head><title></title></head><body>";
	private String feet = "</div></div></body></html>";
	private String url = "";

	public SinaIndex(Document htmlDoc, String url) {
		// TODO Auto-generated constructor stub

		this.htmlDoc = htmlDoc;
		this.url = url;
	}

	public void Head() {
		// TODO Auto-generated method stub

	}

	public void Body() {
		// TODO Auto-generated method stub
		// Elements divs = body.select("[action-type=feed_list_item]");
		// System.out.println(divs.size());
		// for (Element div : divs) {
		// System.out.println(div.data());
		// }

		String str = htmlDoc.html();

		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(
				"<div [\\s\\S]*?action-type=\\\\\"feed_list_item\\\\\"[\\s\\S]*?>[\\s\\S]*?<div [\\s\\S]*?node-type=\\\\\"feed_list_repeat\\\\\"[\\s\\S]*?>");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find())
			ls.add(matcher.group());
		// String[] list=str.split("<div
		// [\\s\\S]*?action-type=\\\\\"feed_list_item\\\\\"[\\s\\S]*?>[\\s\\S]*?<div
		// [\\s\\S]*?node-type=\\\\\"feed_list_repeat\\\\\"[\\s\\S]*?>");
		System.out.println(ls.size());
		for (int i = 0; i < ls.size(); i++) {
			String html = head + ls.get(i) + feet;
			// System.out.println(html);
			// System.out.println("============");
			// File file1 =new File("1.txt");
			// try {
			// HttpUtil.writeTxtFile(html, file1);
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			html = html.replaceAll("\\\\\"", "\"");
			html = html.replaceAll("\\\\r", "");
			html = html.replaceAll("\\\\n", "");
			html = html.replaceAll("\\\\t", "");
			// File file2 =new File("2.txt");
			// try {
			// HttpUtil.writeTxtFile(html, file2);
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			Document doc = Jsoup.parse(html);
			Elements ele = doc.body().getElementsByClass("WB_from");
			// System.out.println("=================");
			// System.out.println(doc.html());

			// File file3 =new File("3.txt");
			// try {
			// HttpUtil.writeTxtFile(doc.html(), file3);
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			for (Element e : ele) {
				System.out.println(e.text());
			}

			Elements hrf = doc.body().getElementsByClass("WB_info");
		}

	}

}
