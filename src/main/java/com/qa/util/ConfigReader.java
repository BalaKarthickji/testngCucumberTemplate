package com.qa.util;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties prop;
	public Properties init_prop() {
		prop = new Properties();
		try {
			FileInputStream fip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(fip);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return prop;
	}
	
}
