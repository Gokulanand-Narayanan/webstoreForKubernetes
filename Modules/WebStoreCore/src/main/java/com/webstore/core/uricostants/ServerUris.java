/*
 * #%L
 * jVoiD Open Platform
 * %%
 * Copyright 2014-2015 Schogini Systems Pvt Ltd (http://www.schogini.com)
 * Project Website: http://www.jvoid.com
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.webstore.core.uricostants;


import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder; 
/**
 * ServerUris class
 * 
 * @author Shajir
 * @version 1.0
 */

public class ServerUris {
	private static final String IP = "localhost";
	private static final String coreDomainUri = "http://"+IP;
	private static final String productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
	private static final String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	private static final String orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
	private static final String customerDomainUri = "http://"+"customers.apm.eginnovations.com"+":7070";
	
	public static final String CORE_SERVER_URI = coreDomainUri + "/bigstoreCoreEngine";
	public static final String PRODUCT_SERVER_URI = productDomainUri + "/products";
	public static final String QUOTE_SERVER_URI = quoteDomainUri + "/cart";
	public static final String ORDER_SERVER_URI = orderDomainUri + "/order";
	public static final String CUSTOMER_SERVER_URI = customerDomainUri + "/customers";
	
public String orderId;
	
	public static String getName(Object obj){
		System.out.println("Inside Get Name Method ... ");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		JSONObject jsonObj = new JSONObject();
		HttpServletRequest req=(HttpServletRequest)obj;
		String productId=req.getParameter("prodId");
		try {
			
			jsonObj.put("productId", Integer.parseInt(productId));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI+URIConstants.GET_ITEM_CODE)
		        .queryParam("params", jsonObj);	
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		//System.out.println(">>>>>>>>>>>returnString.>>>>>>>>>>>"+returnString.getBody());
		
		return returnString.getBody();
		
	}

}
