
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
import org.springframework.http.MediaType;
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
	
	static {
		updateConfigDetails();
	}
	
	
	public static void updateConfigDetails() {
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = ServerUris.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				
//				String hostString = prop.getProperty("core_domain");
				String hostString = System.getenv("core_domain");
				if(hostString!=null && hostString.length() > 0) {
					IP = hostString;
					coreDomainUri = "http://"+IP;
				}
				else {
					IP = "localhost";
					coreDomainUri = "http://"+IP;
				}
				
//				String paymentDomain = prop.getProperty("payment_domain");
				String paymentDomain = System.getenv("payment_domain");
				if(paymentDomain!=null && paymentDomain.length() > 0) {
					paymentDomainURI = "http://"+paymentDomain;
				}
				else {
					paymentDomainURI = "http://payment.apm.eginnovations.com:9190";
				}
				
//				String productHost = prop.getProperty("product_domain");
				String productHost = System.getenv("product_domain");
				if(productHost!=null && productHost.length() > 0) {
					productDomainUri = "http://"+productHost;
				}
				else {
					productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
				}
				
//				String cartHost = prop.getProperty("cart_domain");
				String cartHost = System.getenv("cart_domain");
				if(cartHost!=null && cartHost.length() > 0) {
					quoteDomainUri = "http://"+cartHost;
				}
				else {
					quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
				}
				
//				String ordersHost = prop.getProperty("order_domain");
				String ordersHost = System.getenv("order_domain");
				if(ordersHost!=null && ordersHost.length() > 0) {
					orderDomainUri = "http://"+ordersHost;
				}
				else {
					orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
				}
				
//				String customerDomain = prop.getProperty("customer_domain");
				String customerDomain = System.getenv("customer_domain");
				if(customerDomain!=null && customerDomain.length() > 0) {
					customerDomainUri = "http://"+customerDomain;
				}
				else {
					customerDomainUri = "http://"+"customers.apm.eginnovations.com"+":7070";
				}
				
//				String mqdomain = prop.getProperty("activemq_domain");
				String mqdomain = System.getenv("activemq_domain");
				if(mqdomain!=null && mqdomain.length() > 0) {
					activeMQUrl = "tcp://"+mqdomain;
				}
				else {
					activeMQUrl = "tcp://localhost:61616";
				} 
				
//				String jms_queue = prop.getProperty("jms_queue");
				String jms_queue = System.getenv("jms_queue");
				if(jms_queue!=null && jms_queue.length() > 0) {
					queueName = jms_queue;
				}
				else {
					queueName = "shipping-queue";
				}
				
				
				String productCtxString = prop.getProperty("product_context");
				if(productCtxString!=null && productCtxString.length() > 0) {
					productContext = productCtxString;
				}
				else {
					productContext = "webstoreproducts";
				}
				
				String cartCtxString = prop.getProperty("cart_context");
				if(cartCtxString!=null && cartCtxString.length() > 0) {
					cartContext = cartCtxString;
				}
				else {
					cartContext = "webstorequote";
				}
				
				String orderCtxString = prop.getProperty("order_context");
				if(orderCtxString!=null && orderCtxString.length() > 0) {
					orderContext = orderCtxString;
				}
				else {
					orderContext = "webstoreorder";
				}
				
				String custCtxString = prop.getProperty("customer_context");
				if(custCtxString!=null && custCtxString.length() > 0) {
					customerContext = custCtxString;
				}
				else {
					customerContext = "webstorecustomers";
				}
				
			} else {
				IP = "localhost";
				coreDomainUri = "http://"+IP;
				productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
				quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
				orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
				customerDomainUri = "http://"+"customers.apm.eginnovations.com"+":7070";
				activeMQUrl = "tcp://localhost:61616";
				queueName = "shipping-queue";
				productDomainUri = "http://payment.apm.eginnovations.com:9190";
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
			productDomainUri = "http://payment.apm.eginnovations.com:9190";
			productContext = "webstoreproducts";
			cartContext = "webstorequote";
			orderContext ="webstoreorder";
			customerContext = "webstorecustomers";
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
	
	
	//public static final String CORE_SERVER_URI = coreDomainUri + "/bigstoreCoreEngine";
	public static final String PRODUCT_SERVER_URI = productDomainUri + "/"+productContext;
	public static final String QUOTE_SERVER_URI = quoteDomainUri + "/"+cartContext;
	public static final String ORDER_SERVER_URI = orderDomainUri + "/"+orderContext;
	public static final String CUSTOMER_SERVER_URI = customerDomainUri + "/"+customerContext;
	public static final String PAYMENT_SERVER_URI = paymentDomainURI;
	public String orderId;
	
	/*public static String getName(Object obj){
		HttpServletRequest req=(HttpServletRequest)obj;
		String productId = req.getAttribute("productIdString").toString();
		System.out.println(" Returning Product ID From getName Static Method :"+productId);
		return productId;
	}*/
	public static String getName(Object obj)
	  {
	    System.out.println("Inside Get Name Method ... ");
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Accept", "application/json");
	    JSONObject jsonObj = new JSONObject();
	    HttpServletRequest req = (HttpServletRequest)obj;
	    String productId = req.getParameter("productId");
	    updateConfigDetails();
	    System.out.println("getName.productId   ::::::: "+productId +"    obj  "+obj);
	    try
	    {
	      jsonObj.put("productId", Integer.parseInt(productId));
	    }
	    catch (JSONException e)
	    {
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
