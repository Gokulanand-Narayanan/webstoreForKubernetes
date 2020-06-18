
package com.webstore.core.uriconstants;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder; 


public class ServerUris {
	
	private static InputStream inputStream;
	
	private static String IP = null;
	private static String coreDomainUri = null;
	private static String productDomainUri = null;
	private static String quoteDomainUri = null;
	private static String orderDomainUri = null;
	private static String customerDomainUri = null;
	private static String activeMQUrl = null;
	private static String queueName = null;
	private static String paymentDomainURI = null;
	
	private static String productContext = null;
	private static String cartContext = null;
	private static String orderContext = null;
	private static String customerContext = null;
	private static boolean standAloneOS  = false;
	private static boolean otherOS  = false;
	private static boolean enableKubCtlproblemCode = false;
	
	public static String PRODUCT_SERVER_URI = null;
	public static String QUOTE_SERVER_URI = null;
	public static String ORDER_SERVER_URI = null;
	public static String CUSTOMER_SERVER_URI = null;
	public static String PAYMENT_SERVER_URI = null;
	public static boolean ENABLE_KUBCTL_CODE = enableKubCtlproblemCode;
	public String orderId;
	
	static {
		updateConfigDetails();
	}
	
	public static void main(String[] args) {
		
	}
	public static void updateConfigDetails() {
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = ServerUris.class.getClassLoader().getResourceAsStream(propFileName);
			System.out.println("ServerUris reading props data inputStream object : "+inputStream);
			if (inputStream != null) {
				prop.load(inputStream);
				
				checkPlatformType(prop);
				
				if(standAloneOS) {
					updatePropsConfigDetails(prop,"STL_");
				}else {
					updatePropsConfigDetails(prop,"AWS_");
				}
				updateFinalUrlsDetails();
				printUrlsDetails();
			} else {
				IP = "localhost";
				coreDomainUri = "http://"+IP;
				productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
				quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
				orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
				customerDomainUri = "http://"+"customers.apm.eginnovations.com"+":7070";
				activeMQUrl = "tcp://localhost:61616";
				queueName = "shipping-queue";
				productDomainUri = "http://payment.apm.eginnovations.com:9090";
				productContext = "webstoreproducts";
				cartContext = "webstorequote";
				orderContext ="webstoreorder";
				customerContext = "webstorecustomers";
				System.out.println("INFO : property file '" + propFileName + "' not found in the classpath. Considering Default Values ");
			}
 		}
		catch(Exception e) {
			IP = "localhost";
			coreDomainUri = "http://"+IP;
			productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
			quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
			orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
			customerDomainUri = "http://"+"customers.apm.eginnovations.com"+":7070";
			activeMQUrl = "tcp://localhost:61616";
			queueName = "shipping-queue";
			productDomainUri = "http://payment.apm.eginnovations.com:9090";
			productContext = "products";
			cartContext = "quote";
			orderContext ="order";
			customerContext = "customers";
			e.printStackTrace();
		}
		finally{
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
	}
	
	
	private static void updateFinalUrlsDetails() {
		PRODUCT_SERVER_URI = productDomainUri + "/"+productContext;
		QUOTE_SERVER_URI = quoteDomainUri + "/"+cartContext;
		ORDER_SERVER_URI = orderDomainUri + "/"+orderContext;
		CUSTOMER_SERVER_URI = customerDomainUri + "/"+customerContext;
		PAYMENT_SERVER_URI = paymentDomainURI;
	}


	private static void printUrlsDetails() {
		System.out.println("ENABLE_AWS_PROBLEM_CODE : "+enableKubCtlproblemCode);
		System.out.println("CORE_DOMAIN : "+coreDomainUri);
		System.out.println("PRODUCT_DOMAIN : "+PRODUCT_SERVER_URI);
		System.out.println("CART_DOMAIN : "+QUOTE_SERVER_URI);
		System.out.println("ORDER_DOMAIN : "+ORDER_SERVER_URI);
		System.out.println("CUSTOMER_DOMAIN : "+CUSTOMER_SERVER_URI);
		System.out.println("PAYMENT_DOMAIN : "+PAYMENT_SERVER_URI); 
	}


	private static void updatePropsConfigDetails(Properties prop, String string) {
		try {
			String kubCtlProblemCode = prop.getProperty(string+"ENABLE_AWS_PROBLEM_CODE");
			String enableSSLString = prop.getProperty("WEBSTORE_ENABLE_SSL");
			boolean enableSSL = false;
			try {
				enableSSL = Boolean.parseBoolean(enableSSLString);
				System.out.println("SSL Enabled For Webstore application Server : "+enableSSL);
			} catch (Exception e) {
				enableSSL=false;
			}
			
			if(kubCtlProblemCode != null && kubCtlProblemCode.length() > 0) {
				try {
					enableKubCtlproblemCode = Boolean.parseBoolean(kubCtlProblemCode);
				} catch (Exception e) {
					enableKubCtlproblemCode=false;
				}
			}
			
			String hostString = prop.getProperty(string+"CORE_DOMAIN");
			if(hostString!=null && hostString.length() > 0 && enableSSL) {
				IP = hostString;
				coreDomainUri = "https://"+IP;
			}
			else {
				IP = "localhost";
				coreDomainUri = "http://"+IP;
			}
			
			String paymentDomain = prop.getProperty(string+"PAYMENT_DOMAIN");
			if(paymentDomain!=null && paymentDomain.length() > 0 && enableSSL) {
				paymentDomainURI = "https://"+paymentDomain;
			}
			else {
				paymentDomainURI = "http://"+paymentDomain;
			}
			
			String productHost = prop.getProperty(string+"PRODUCT_DOMAIN");
			if(productHost!=null && productHost.length() > 0 && enableSSL) {
				productDomainUri = "https://"+productHost;
			}
			else {
				productDomainUri = "http://"+productHost;
			}
			
			String cartHost = prop.getProperty(string+"CART_DOMAIN");
			if(cartHost!=null && cartHost.length() > 0 && enableSSL) {
				quoteDomainUri = "https://"+cartHost;
			}else {
				quoteDomainUri = "http://"+cartHost;
			}
			
			
			String ordersHost = prop.getProperty(string+"ORDER_DOMAIN");
			if(ordersHost!=null && ordersHost.length() > 0 && enableSSL) {
				orderDomainUri = "https://"+ordersHost;
			}
			else {
				orderDomainUri = "http://"+ordersHost;
			}
			
			String customerDomain = prop.getProperty(string+"CUSTOMER_DOMAIN");
			if(customerDomain!=null && customerDomain.length() > 0 && enableSSL) {
				customerDomainUri = "https://"+customerDomain;
			}
			else {
				customerDomainUri = "http://"+customerDomain;
			}
			
			String mqdomain = prop.getProperty(string+"ACTIVEMQ_DOMAIN");
			if(mqdomain!=null && mqdomain.length() > 0 ) {
				activeMQUrl = "tcp://"+mqdomain;
			}
			else {
				activeMQUrl = "tcp://localhost:61616";
			} 
			
			String jms_queue = prop.getProperty(string+"JMS_QUEUE_NAME");
			if(jms_queue!=null && jms_queue.length() > 0) {
				queueName = jms_queue;
			}
			else {
				queueName = "shipping-queue";
			}
			
			
			String productCtxString = prop.getProperty("PRODUCT_CONTEXT");
			if(productCtxString!=null && productCtxString.length() > 0) {
				productContext = productCtxString;
			}
			else {
				productContext = "products";
			}
			
			String cartCtxString = prop.getProperty("CART_CONTEXT");
			if(cartCtxString!=null && cartCtxString.length() > 0) {
				cartContext = cartCtxString;
			}
			else {
				cartContext = "quote";
			}
			
			String orderCtxString = prop.getProperty("ORDER_CONTEXT");
			if(orderCtxString!=null && orderCtxString.length() > 0) {
				orderContext = orderCtxString;
			}
			else {
				orderContext = "order";
			}
			
			String custCtxString = prop.getProperty("CUSTOMER_CONTEXT");
			if(custCtxString!=null && custCtxString.length() > 0) {
				customerContext = custCtxString;
			}
			else {
				customerContext = "customers";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	private static void checkPlatformType(Properties prop) {
		try {
			String standAloneType = prop.getProperty("WEBSTORE_STAND_ALONE");
			standAloneOS = Boolean.parseBoolean(standAloneType);
			System.out.println("Webstore application Running in [STAND-ALONE] mode : "+standAloneOS);
			
			String otherOSType = prop.getProperty("WEBSTORE_AWS");
			otherOS = Boolean.parseBoolean(otherOSType);
			System.out.println("Webstore application Running in [AWS] mode : "+otherOS);
			
		} catch (Exception e) {
			standAloneOS = true;
		}
		if(!(standAloneOS ^ otherOS)) {
			System.out.println("both option for platform type are same assuming Webstore application Running in STAND-ALONE mode");
			System.out.println("please check the platform option in props file");
			standAloneOS = true;
		}		
	}


	//public static final String CORE_SERVER_URI = coreDomainUri + "/bigstoreCoreEngine";
	
	/*public static String getName(Object obj){
		HttpServletRequest req=(HttpServletRequest)obj;
		String productId = req.getAttribute("productIdString").toString();
		System.out.println(" Returning Product ID From getName Static Method :"+productId);
		return productId;
	}*/
	public static String getName(Object obj){
	    System.out.println("Inside Get Name Method ... ");
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Accept", "application/json");
	    JSONObject jsonObj = new JSONObject();
	    HttpServletRequest req = (HttpServletRequest)obj;
	    String productId = req.getParameter("productId");
	    updateConfigDetails();
	    System.out.println("getName.productId   ::::::: "+productId +"    obj  "+obj);
	    try{
	      jsonObj.put("productId", Integer.parseInt(productId));
	    }
	    catch (JSONException e){
	      e.printStackTrace();
	    }
	    System.out.println("ServerUris.QUOTE_SERVER_URI   ::::::: "+ServerUris.QUOTE_SERVER_URI);
	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI+URIConstants.GET_ITEM_CODE).queryParam("params", jsonObj); 
	    HttpEntity<?> entity = new HttpEntity(headers);
	    HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
	   
	    return (String)returnString.getBody();
	}
	
	public static String getActiveMQDomain() {
		return activeMQUrl;
	}
	
	public static String getJMSQueueName() {
		return queueName;
	}
	
	public static String getPaymentDomainURI() {
		return paymentDomainURI;
	}
}
