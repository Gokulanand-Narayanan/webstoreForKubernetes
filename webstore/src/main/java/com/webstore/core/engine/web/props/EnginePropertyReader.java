package com.webstore.core.engine.web.props;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class EnginePropertyReader {

	public Properties getEnginePropsObj() {
		InputStream inputStream = null;
		String propFileName = "engine.props";
		try {
			Properties props = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				props.load(inputStream);
				return props;
			} 
		} catch (Exception exception) {
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException fileNotFound) {
				fileNotFound.printStackTrace();
			}
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception inputStreamException) {
					inputStreamException.printStackTrace();
				}
			}
		}
		return null;
	}
}
