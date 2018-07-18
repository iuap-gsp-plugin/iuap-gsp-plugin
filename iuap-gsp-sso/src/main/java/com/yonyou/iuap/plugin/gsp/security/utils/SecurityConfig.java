package com.yonyou.iuap.plugin.gsp.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecurityConfig {
	
	private static Properties properties = new Properties();
	static{
		InputStream ins = SecurityConfig.class.getClassLoader().getResourceAsStream("application-gsp.properties");
		try {
			properties.load(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getString(String key) {
		return properties.getProperty(key);
	}
	
	public static int getInt(String key, int defaultValue) {
		String value = getString(key);
		if(value==null || value.trim().length()==0) {
			return defaultValue;
		}else {
			return Integer.parseInt(value.trim());
		}
	}
	

}
