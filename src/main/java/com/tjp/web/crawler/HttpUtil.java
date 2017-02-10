/**
 * 
 */
package com.tjp.web.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tjp.web.crawler.download.Download;
import com.tjp.web.crawler.html.zhihu.ZhiHuIndex;
import com.tjp.web.crawler.html.zhihu.ZhiHuQuestion;

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

	public static String http(String connUrl, Map<String, String> param, int requestMethod,
			Map<String, String> cookieMap) {
		StringBuilder sbLines = new StringBuilder("");
		try {

			String urlStr = connUrl;
			String requestStr = GetMethodStr;
			String value = "";
			if (param != null && param.size() > 0) {
				value = Map2Str(param, "&");
			}

			String cookieStr = "";
			if (cookieMap != null && cookieMap.size() > 0) {
				cookieStr = Map2Str(cookieMap, ";");
			}

			if (requestMethod == RequestMethod.GET) {
				if (!"".equals(value)) {
					urlStr += "?" + value;
				}
				requestStr = GetMethodStr;
			} else {
				requestStr = PostMethodStr;
			}

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(requestStr);
			connection.setDoOutput(true);
			String cookieStr1="q_c1=d295c7ff34d941e8b902a9d78644526a|1486460189000|1486460189000; l_cap_id=\"ODE1MTI2NThmMDc3NDgzYzgzMWNiODU0NzczNzE5NWU=|1486460189|a488dba0e702be812b20addb8c0531e19a85fa80\"; cap_id=\"NzIxZWJmZTI4ZmU5NDNhMDkyZjZiZDgzYTE4MmE1Yzk=|1486460189|89928699849c2a48ce60b2b2f3f37fffe90d0ec1\"; d_c0=\"AICC6koPRguPTodBWsOJFvsIPlydyKYHYiI=|1486460190\"; _zap=5ae116c3-0e4b-448f-b2b5-61312349120e; login=\"MTJhNjYxYmU4N2VjNDA5MjliOWE2NjMyNDRkYWU4NjU=|1486460210|966bbf3edc4955202211a2439ca2de982939bfac\"; _xsrf=35ba42c90c5bb0cb15403fde790900a3; aliyungf_tc=AQAAABYN22c1EQYARHgDZw26NILY81l6; __utma=51854390.1463065393.1486632932.1486691488.1486694894.3; __utmb=51854390.0.10.1486694894; __utmc=51854390; __utmz=51854390.1486460121.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.100-1|2=registration_date=20131115=1^3=entry_date=20131115=1; z_c0=Mi4wQUFCQXRMc2dBQUFBZ0lMcVNnOUdDeGNBQUFCaEFsVk5NeUxCV0FBd0xXeHhTdU0xeE8xcUtWRVJ2S0VaMjBqaDJn|1486695005|7df9ef600a93a9cfc39fa09e1821fef061478d3d; nweb_qa=heifetz";
			String cookieLogin="q_c1=d295c7ff34d941e8b902a9d78644526a|1486460189000|1486460189000; l_cap_id=\"ODE1MTI2NThmMDc3NDgzYzgzMWNiODU0NzczNzE5NWU=|1486460189|a488dba0e702be812b20addb8c0531e19a85fa80\"; cap_id=\"NzIxZWJmZTI4ZmU5NDNhMDkyZjZiZDgzYTE4MmE1Yzk=|1486460189|89928699849c2a48ce60b2b2f3f37fffe90d0ec1\"; d_c0=\"AICC6koPRguPTodBWsOJFvsIPlydyKYHYiI=|1486460190\"; _zap=5ae116c3-0e4b-448f-b2b5-61312349120e; login=\"MTJhNjYxYmU4N2VjNDA5MjliOWE2NjMyNDRkYWU4NjU=|1486460210|966bbf3edc4955202211a2439ca2de982939bfac\"; aliyungf_tc=AQAAAN8BuBP0tA0ARHgDZ6RzSCF/UUUL; _xsrf=35ba42c90c5bb0cb15403fde790900a3; z_c0=Mi4wQUFCQXRMc2dBQUFBZ0lMcVNnOUdDeGNBQUFCaEFsVk5NeUxCV0FBd0xXeHhTdU0xeE8xcUtWRVJ2S0VaMjBqaDJn|1486463313|bbe9a56f414da412b43abc1897edc57520ded982; nweb_qa=heifetz; __utma=51854390.1674498487.1486460121.1486460121.1486463124.2; __utmb=51854390.0.10.1486463124; __utmc=51854390; __utmz=51854390.1486460121.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=51854390.100-1|2=registration_date=20131115=1^3=entry_date=20131115=1";
			if (!"".equals(cookieStr)) {
				connection.setRequestProperty("Cookie",cookieStr1);
				connection.setRequestProperty("Host","www.zhihu.com");
			}

			if (requestMethod == RequestMethod.POST) {
				if (!"".equals(value)) {
					OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
					writer.write(value);
					writer.close();
				}

			}

			// if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			//
			// }

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
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String url="https://www.zhihu.com/question/26427084";
		// map.put("login",
		// "MTJhNjYxYmU4N2VjNDA5MjliOWE2NjMyNDRkYWU4NjU=|1486460210|966bbf3edc4955202211a2439ca2de982939bfac");
		map.put("z_c0",
				"Mi4wQUFCQXRMc2dBQUFBZ0lMcVNnOUdDeGNBQUFCaEFsVk5NeUxCV0FBd0xXeHhTdU0xeE8xcUtWRVJ2S0VaMjBqaDJn|1486463313|bbe9a56f414da412b43abc1897edc57520ded982");
		map.put("nweb_qa", "heifetz");
		String html = HttpUtil.http(url, null, HttpUtil.RequestMethod.GET, map);
		System.out.println(html);
		Document doc = Jsoup.parse(html);

		new ZhiHuQuestion(doc, url).Body();
		
		
		
		Download.getInstance().shutDown();
		

	}

}
