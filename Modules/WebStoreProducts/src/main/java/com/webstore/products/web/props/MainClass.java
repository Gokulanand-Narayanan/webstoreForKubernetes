package com.webstore.products.web.props;

import java.util.Properties;

public class MainClass {

	public static void main(String[] args) {
		CartPropertyReader engineProps = new CartPropertyReader();
		Properties enginePropsObj = engineProps.getCartPropsObj();
		String getUrl1 = enginePropsObj.getProperty("GET_URL_1");
		String getUrl2 = enginePropsObj.getProperty("GET_URL_2");
		String userAgent = enginePropsObj.getProperty("USER_AGENT");
		String query = enginePropsObj.getProperty("QUERY");
		String dataSource = enginePropsObj.getProperty("DATA_SOURCE_JNDI_JBOSS");
		
		System.out.println(" Get Url 1 : "+getUrl1);
		System.out.println(" Get Url 2 : "+getUrl2);
		System.out.println(" User Agent : "+userAgent);
		System.out.println(" Query Params : "+query);
		System.out.println(" DataSource Jndi : "+dataSource);
		
	}
}
