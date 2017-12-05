package com.daoxue.zhidao.ocr.util;

import java.io.InputStream;
import java.util.Properties;



public class PropertiesUtil {

	public static String readConfigValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
			props = new Properties();
			props.load(in);
			String value = props.getProperty (key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
