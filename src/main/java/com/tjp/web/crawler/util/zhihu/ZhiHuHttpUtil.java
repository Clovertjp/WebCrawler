package com.tjp.web.crawler.util.zhihu;

import java.util.Map;

import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.util.HttpUtil;

public class ZhiHuHttpUtil extends HttpUtil {
	
	public String http(String urlKey,String connUrl, Map<String, String> param, int requestMethod,
			Map<String, String> cookieMap) {
		
		if(!Config.URL_KEYS.contains(urlKey))
		{
			return null;
		}
		
		return super.http(connUrl, param, requestMethod, cookieMap, Config.URL_PARAM_MAP.get(urlKey));
		
	}

}
