
package com.webstore.quote.uriconstants;

import java.io.InputStream;
import java.util.Properties;

public class URIConstants {
	public static final String GET_PRODUCT = "/catalog/product";
	private static final String DEFAULT_PRODUCT_CONTEXT = "webstoreproducts";

	private static InputStream inputStream;
	private static String productDomainUri = null;
	private static String productContext = DEFAULT_PRODUCT_CONTEXT;	
	static {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			inputStream = URIConstants.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				String productHost = prop.getProperty("product_domain");
				String productContextString = prop.getProperty("product_context");
				if(productContextString!=null && productContextString.length() > 0) {
					productContext = productContextString;
				}
				else {
					productContext = DEFAULT_PRODUCT_CONTEXT;
				}
				if(productHost!=null && productHost.length() > 0) {
					productDomainUri = "http://"+productHost+"/"+productContext;
				}
				else {
					productDomainUri = "http://"+"products.apm.eginnovations.com:4040/"+productContext;
				}
				
			} else {
				productDomainUri = "http://"+"products.apm.eginnovations.com:4040/"+productContext;
				System.out.println("INFO : property file '" + propFileName + "' not found in the classpath. Considering Default Values ");
			}
 		}
		catch(Exception e) {
			productDomainUri = "http://"+"products.apm.eginnovations.com4040/"+productContext;;
			e.printStackTrace();
		}
		finally{
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (Exception e) {}
			}
		}
	}
	
	public static String getProductDomainURI() {
		return productDomainUri;
	}

}
