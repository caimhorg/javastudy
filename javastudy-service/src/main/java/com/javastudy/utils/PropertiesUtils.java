package com.javastudy.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties getProperties(String filePath) {
		Properties prop = new Properties();
		try {
			prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
}
