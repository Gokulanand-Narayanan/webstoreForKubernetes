package com.webstore.core.engine.web.props;

import java.util.Properties;

public class MainClass {

	public static void main(String[] args) {
		EnginePropertyReader engineProps = new EnginePropertyReader();
		Properties enginePropsObj = engineProps.getEnginePropsObj();
		String getUrl = enginePropsObj.getProperty("GET_URL");
		String postUrl = enginePropsObj.getProperty("POST_URL");
		String userAgent = enginePropsObj.getProperty("USER_AGENT");
		String postParams = enginePropsObj.getProperty("POST_PARAMS");
		String dataSource = enginePropsObj.getProperty("DATA_SOURCE_JNDI_NAME");
		
		System.out.println(" Get Url : "+getUrl);
		System.out.println(" Post Url : "+postUrl);
		System.out.println(" User Agent : "+userAgent);
		System.out.println(" Post Params : "+postParams);
		System.out.println(" DataSource Jndi : "+dataSource);
		
	}
}
