/**
 * 
 */
package com.tjp.web.crawler.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.html.zhihu.ZhiHuExplore;
import com.tjp.web.crawler.html.zhihu.ZhiHuQuestion;
import com.tjp.web.crawler.html.zhihu.ZhiHuUser;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserFollow;
import com.tjp.web.crawler.html.zhihu.ZhiHuUserQuestion;
import com.tjp.web.crawler.puredb.model.Answer;

/**
 * @author TangJP
 * @date 2017年1月22日
 */
public class HttpUtil {

	private static final String GetMethodStr = "GET";
	private static final String PostMethodStr = "POST";

	public static class RequestMethod {
		public static int POST = 1;
		public static int GET = 2;
	}

	public String http(String connUrl, Map<String, String> param, int requestMethod,
			Map<String, String> cookieMap,String paramStr) {
		StringBuilder sbLines = new StringBuilder("");
		try {

			String urlStr = connUrl;
			String requestStr = GetMethodStr;
			String value = "";
			if (param != null && param.size() > 0) {
				value = Map2Str(param, "&");
			}
			

			if(!Strings.isNullOrEmpty(paramStr))
			{
				if(!Strings.isNullOrEmpty(value))
				{
					value=value+"&"+paramStr;
				}else
				{
					value=paramStr;
				}
			}

			if (requestMethod == RequestMethod.GET) {
				if (!"".equals(value)) {
					if(urlStr.contains("?"))
					{
						urlStr += "&" + value;
					}else
					{
						urlStr += "?" + value;
					}
					
				}
				requestStr = GetMethodStr;
			} else {
				requestStr = PostMethodStr;
			}

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(requestStr);
			connection.setDoOutput(true);
			if(!Strings.isNullOrEmpty(Config.COOKIE))
			{
				connection.setRequestProperty("Cookie", Config.COOKIE);
			}
			
			if(!Strings.isNullOrEmpty(Config.HOST))
			{
				connection.setRequestProperty("Host", Config.HOST);
			}
			if(!Strings.isNullOrEmpty(Config.AGENT))
			{
				connection.setRequestProperty("User-Agent", Config.AGENT);
			}
			if(!Strings.isNullOrEmpty(Config.AUTHOR))
			{
				connection.setRequestProperty("authorization", Config.AUTHOR);
			}
			

			if (requestMethod == RequestMethod.POST) {
				if (!"".equals(value)) {
					OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
					writer.write(value);
					writer.close();
				}
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String strLine = "";
			while ((strLine = reader.readLine()) != null) {
				sbLines.append(strLine);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sbLines.toString();
	}

	private static String Map2Str(Map<String, String> param, String split) {
		if (param == null || param.size() <= 0) {
			return "";
		}
		StringBuilder strBuilder = new StringBuilder("");
		for (Map.Entry<String, String> entry : param.entrySet()) {
			strBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(split);
		}
		return strBuilder.substring(0, strBuilder.length() - 1);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Config.load();
		Map<String, String> map = new HashMap<String, String>();
		// String url="https://www.zhihu.com/question/26427084";
		String url = "https://www.zhihu.com/api/v4/questions/25975218/answers";
		map.put("limit", "5");
		map.put("offset", "0");
		map.put("sort_by", "default");
//		String html = HttpUtil.http(url, null, HttpUtil.RequestMethod.GET, map);
//		System.out.println(html);
//		Document doc=Jsoup.parse(html);
//		new ZhiHuQuestion(doc, url).Body();

	}

}
