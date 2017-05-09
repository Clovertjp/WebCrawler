package com.tjp.web.crawler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.google.common.base.Strings;

public class PropertiesUtil {

	private final String fileName = "config/config.properties";

	private static PropertiesUtil propertiesUtil = new PropertiesUtil();

	private Properties properties = null;

	public static PropertiesUtil getInstance() {
		return propertiesUtil;
	}

	private PropertiesUtil() {
		properties = new Properties();
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			properties.load(fileInput);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProperty(String key, String defaultValue) {
		if (Strings.isNullOrEmpty(key)) {
			return defaultValue;
		}
		if (properties == null) {
			return defaultValue;
		}

		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return defaultValue;

	}

	public String getProperty(String key) {
		return getProperty(key, null);
	}
	
	

	public Properties getProperties() {
		return properties;
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getInstance().getProperty("cookie"));
	}

}
