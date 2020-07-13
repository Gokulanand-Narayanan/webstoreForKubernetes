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
package com.webstore.quote.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.webstore.quote.model.CheckoutAddToCart;
import com.webstore.quote.model.CheckoutQuote;
import com.webstore.quote.model.CheckoutQuoteAddress;
import com.webstore.quote.model.CheckoutQuoteItem;
import com.webstore.quote.model.DataDao;
import com.webstore.quote.model.ProductsMaster;
import com.webstore.quote.service.CheckoutQuoteAddressService;
import com.webstore.quote.service.CheckoutQuoteItemService;
import com.webstore.quote.service.CheckoutQuoteService;
import com.webstore.quote.singlton.DatabaseConnection;
import com.webstore.quote.update.JsonDataUpdate;
import com.webstore.quote.uriconstants.URIConstants;
import com.webstore.quote.utils.Utilities;
import com.webstore.quote.service.MobileVendorService;


@Controller
@RequestMapping("/")
public class QuoteController {	
	
	@Autowired
	private CheckoutQuoteService checkoutQuoteService;
	
	@Autowired
	private CheckoutQuoteItemService checkoutQuoteItemService;
	
	@Autowired
	private MobileVendorService mobileVendorService;
	

	@Autowired
	private CheckoutQuoteAddressService checkoutQuoteAddressService;
	
	public void setCheckoutQuoteService(CheckoutQuoteService checkoutQuoteService){
		this.checkoutQuoteService = checkoutQuoteService;
	}
	
	public void setCheckoutQuoteItemService(CheckoutQuoteItemService checkoutQuoteItemService) {
		this.checkoutQuoteItemService = checkoutQuoteItemService;
	}
	
	public void setCheckoutQuoteAddressService(
			CheckoutQuoteAddressService checkoutQuoteAddressService) {
		this.checkoutQuoteAddressService = checkoutQuoteAddressService;
	}

	public void setMobileVendorService(MobileVendorService mobileVendorService) {
		this.mobileVendorService = mobileVendorService;
	}

	
	@RequestMapping
    public @ResponseBody String list() {
		return "Welcome to Quote";
	}
	
	@RequestMapping(value = "/quote/addToCartServer", method = RequestMethod.GET)
	public void addToCartServer() {
		try {
			System.out.println(" Adding items into cart server for processing....");
			getCustomerDetails();
			System.out.println(" Got the Customer Details for products.... "); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/quote/getItemCode", method = RequestMethod.GET)
	public @ResponseBody String getItemCode(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response){
		int cartId = -1;
		try {
			cartId = jsonParams.getInt("productId");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Making entry to CheckoutQuoteItem
				ProductsMaster productItem = getTelcoProduct(cartId);
				System.out.println("-------------------"+productItem.getSku());
				if(productItem!=null){
					return productItem.getSku();
				}
		
		return "None";
	}
	
	
	@RequestMapping(value = "/quote/add", method = RequestMethod.GET)
	public @ResponseBody String addCart(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response){
//		String jstr = "{\"cartId\":-1, \"productId\":2, \"attributeId\":1, \"quantity\":2}";
//		String jstr = "{\"cartId\":1, \"productId\":3, \"attributeId\":1, \"quantity\":2}";
		
		int cartId = -1;
		int productId = -1;
		int attributeId = -1;
		int quantity = -1;
		try {
			cartId = jsonParams.getInt("cartId");
			productId = jsonParams.getInt("productId");
			attributeId = jsonParams.getInt("attributeId");
			quantity = jsonParams.getInt("quantity");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
//		cartId = 1;
//		productId = 2;
//		attributeId = 1;
//		quantity = 2;
		
		CheckoutAddToCart checkoutAddToCart = new CheckoutAddToCart();
		checkoutAddToCart.setCartId(cartId);
		checkoutAddToCart.setProductId(productId);
		checkoutAddToCart.setAttributeId(attributeId);
		checkoutAddToCart.setQuantity(quantity);
		/*for(int i = 0; i < 4; i++) {
			addToCart("Name","itemCode","quntity");
		}*/
		//Making entry to CheckoutQuote
		int insertedCartId = -1;
		CheckoutQuote checkoutQuote = null;
		try {
			checkoutQuote = new CheckoutQuote();
			checkoutQuote.setId(checkoutAddToCart.getCartId());
						
			if (checkoutQuote.getId() == -1) {
				System.out.println("Setting create id");
				checkoutQuote.setId(0);
				checkoutQuote.setCreatedAt(Utilities.getCurrentDateTime());
				checkoutQuote.setBaseGrandTotal(0);
				checkoutQuote.setBaseSubtotal(0);
				checkoutQuote.setGrandTotal(0);
				checkoutQuote.setSubtotal(0);
				checkoutQuote.setUpdatedAt(Utilities.getCurrentDateTime());
				this.checkoutQuoteService.addCheckoutQuote(checkoutQuote);
			}
			else {
				checkoutQuote = this.checkoutQuoteService.getCheckoutQuoteById(cartId);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			insertedCartId = checkoutQuote.getId();
			System.out.println("CheckoutQuote ID: insertedCartId : "+insertedCartId);
			
			//Making entry to CheckoutQuoteItem
			ProductsMaster productItem = getTelcoProduct(checkoutAddToCart.getProductId());
			
			CheckoutQuoteItem checkoutQuoteItem = this.checkoutQuoteItemService.getCheckoutQuoteItem(cartId, productId);
			int addingNew = 0;
			if (checkoutQuoteItem == null) {
				addingNew = 1;
				checkoutQuoteItem = new CheckoutQuoteItem();
				checkoutQuote.setItemsCount(checkoutQuote.getItemsCount()+1);
				checkoutQuoteItem.setCreatedAt(Utilities.getCurrentDateTime());
			}
			
			checkoutQuoteItem.setWeight(productItem.getWeight());
			checkoutQuoteItem.setQuantity(checkoutQuoteItem.getQuantity()+checkoutAddToCart.getQuantity());
			checkoutQuoteItem.setSku(productItem.getSku());
			checkoutQuoteItem.setPrice(productItem.getPrice());
			checkoutQuoteItem.setBasePrice(productItem.getPrice());
			checkoutQuoteItem.setDescription(productItem.getDescription());
			checkoutQuoteItem.setName(productItem.getName());
			checkoutQuoteItem.setProductId(productItem.getId());
			checkoutQuoteItem.setQuoteId(insertedCartId);
			
//			if (checkoutAddToCart.getCartId() == -1) {
				checkoutQuote.setItemsQuantity(checkoutQuote.getItemsQuantity()+checkoutAddToCart.getQuantity());
//			}
//			else {
//				checkoutQuoteItem.setCreatedAt(this.checkoutQuoteItemService.getCheckoutQuoteItemById(checkoutQuoteItem.getId()).getCreatedAt());
//			}
			checkoutQuoteItem.setRowTotal(checkoutQuoteItem.getPrice()*checkoutQuoteItem.getQuantity());
			checkoutQuoteItem.setBaseRowTotal(checkoutQuoteItem.getBasePrice()*checkoutQuoteItem.getQuantity());
			checkoutQuoteItem.setRowWeight(checkoutQuoteItem.getWeight()*checkoutQuoteItem.getQuantity());
			checkoutQuoteItem.setUpdatedAt(Utilities.getCurrentDateTime());
			
			this.checkoutQuoteItemService.addCheckoutQuoteItem(checkoutQuoteItem);
			int insertedProductId = checkoutQuoteItem.getId();
			System.out.println("CheckoutItem ID: insertedProductId : "+insertedProductId);
			
			System.out.println("Abhi checkoutquote b4 = "+checkoutQuote.toString());
			
			if (addingNew == 0) {
				checkoutQuote.setBaseSubtotal(checkoutQuote.getBaseSubtotal()+checkoutQuoteItem.getBasePrice()*checkoutAddToCart.getQuantity());
				checkoutQuote.setSubtotal(checkoutQuote.getSubtotal()+checkoutQuoteItem.getPrice()*checkoutAddToCart.getQuantity());
			}
			else {
				checkoutQuote.setBaseSubtotal(checkoutQuote.getBaseSubtotal()+checkoutQuoteItem.getBaseRowTotal());
				checkoutQuote.setSubtotal(checkoutQuote.getSubtotal()+checkoutQuoteItem.getRowTotal());
			}
			
				checkoutQuote.setBaseGrandTotal(checkoutQuote.getBaseSubtotal()+50);
				checkoutQuote.setGrandTotal(checkoutQuote.getSubtotal()+50);
				checkoutQuote.setUpdatedAt(Utilities.getCurrentDateTime());
				
				
				this.checkoutQuoteService.addCheckoutQuote(checkoutQuote);
				System.out.println("checkoutquote after = "+checkoutQuote.toString());
//			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cartId", insertedCartId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//getCustomerDetails();
		return jsonObj.toString();
	}
	
	private String addToCart(String string, String string2, String string3) {
		try {
			//Thread.sleep(80);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "product added to cart...";
	}

	public ProductsMaster getTelcoProduct(int productId) {	
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("id", productId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("param jsonObj=>"+jsonObj.toString());
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URIConstants.getProductDomainURI() + URIConstants.GET_PRODUCT)
		        .queryParam("params", jsonObj);	
		HttpEntity entity = new HttpEntity(headers);
		
		System.out.println( "  (builder.build().toUri()  ====>  "+builder.build().toUri() );
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		
		System.out.println("returnString=>"+returnString);
		
		JSONObject returnJsonObj = null;
		try {
			returnJsonObj = new JSONObject(returnString.getBody());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray productsArr = null;
		ProductsMaster productsMaster = null;
        try {
        	productsArr = returnJsonObj.getJSONArray("products");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	productsMaster = new ProductsMaster();
	    try {
			ObjectMapper mapper = new ObjectMapper();
			try {
				productsMaster = mapper.readValue(productsArr.getJSONObject(0).toString(), ProductsMaster.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return productsMaster;
	}
	
	private int getMax() {
		Random r = new Random();
		int Low = 5;
		int High = 14;
		int random = r.nextInt(High-Low) + Low;
		int max = random * 1000;
		return max;
	}
	
	private boolean validateJsonElements(JSONObject jsonParams) {
		try {
			//Thread.sleep(15L);
		}
		catch(Exception e) {
		}
		return true;
	}

	
	private String processJsonData(JSONObject jsonParams, int maxLimit , int value, boolean checkData) {
		String result = "Success";
/*		int verifiedElement = 0;
		boolean isValid = validateJsonElements(jsonParams);
		if(isValid) {
			try {
				int length = 0;
				int jsonLength = jsonParams.length();
				if(checkData) {
					jsonLength = maxLimit;
				}
				while(length <= jsonLength) {
					for(int j=0;j<value;j++) {
						verifiedElement = verifiedElement + 1;
						if(verifiedElement % 11 == 0) {
							verifiedElement = 0;
						}
					}
					length++;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}*/
		return result;
	}
	
	
	@RequestMapping(value = "/quote/viewcart", method = RequestMethod.GET)
	public @ResponseBody String getCart(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response){
		
		//System.out.println("TOTAL RECS1:"+this.productsMasterService.getAllProducts().size());
		
		int cartId = -1;
		try {
			cartId = jsonParams.getInt("cartId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		int count = 1000000;
//		String processJson = processJsonData(jsonParams, getMax(), count, true);
		JSONObject cartObject = new JSONObject();
		JSONArray quoteItems = new JSONArray();
		
		List<CheckoutQuoteItem> quoteItemsList = null;
		quoteItemsList = this.checkoutQuoteItemService.listCheckoutQuoteItems(cartId);
		System.out.println("ABHI Quote Item List Count= "+quoteItemsList.size());
		ObjectMapper mapper = new ObjectMapper();
		for(int i=0; i<quoteItemsList.size(); i++) {
			
			try {
				String strQuoteItemObj = mapper.writeValueAsString(quoteItemsList.get(i));
				JSONObject jsonObj = new JSONObject(strQuoteItemObj);
				
				quoteItems.put(jsonObj);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		CheckoutQuote currentQuote = this.checkoutQuoteService.getCheckoutQuoteById(cartId);
		
		try {
			cartObject.put("items", quoteItems);
			cartObject.put("total", currentQuote.getGrandTotal());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
						
		// Dummy methods for Call Graph.
		int tot = updateGrandTotal(currentQuote);
		JsonDataUpdate jsonUpdate = new JsonDataUpdate();
		jsonUpdate.parseJSON(cartObject);
		jsonUpdate.getResultJSON();
		
		return cartObject.toString();
	}
	
	private int updateGrandTotal(CheckoutQuote currentQuote) {
		int id = currentQuote.getId();
		int custId = currentQuote.getCustomerId();
		for(int k=0;k<3;k++) {
			getServicePlanDetails(k);
		}
		try {
			//Thread.sleep(15L);
		}
		catch(Exception e) {
		}
		return 10;
	}
	
	private String getServicePlanDetails(int no) {
		try {
			//Thread.sleep(10L);
		}
		catch(Exception e) {
		}
		return "result";
	}
	
	@RequestMapping(value = "/quote/delete", method = RequestMethod.GET)
	public @ResponseBody String deleteCart(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response){
//		String jstr = "{\"cartId\":-1, \"productId\":2, \"attributeId\":1, \"quantity\":2}";
//		String jstr = "{\"cartId\":1, \"productId\":3, \"attributeId\":1, \"quantity\":2}";
		System.out.println("Inside delete");
		int cartId = -1;
		int productId = -1;
		try {
			cartId = jsonParams.getInt("cartId");
			productId = jsonParams.getInt("productId");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
//		cartId = 1;
//		productId = -1;
		
		
		CheckoutQuoteItem quoteItemToDelete = null;
		CheckoutQuote checkoutQuote = new CheckoutQuote();
		List<CheckoutQuoteItem> quoteItemsList = null;
		if (productId != -1) {
			quoteItemToDelete = this.checkoutQuoteItemService.getCheckoutQuoteItem(cartId, productId);
			quoteItemsList = new ArrayList<CheckoutQuoteItem>();
			quoteItemsList.add(quoteItemToDelete);
		}
		else {
			quoteItemsList = this.checkoutQuoteItemService.listCheckoutQuoteItems(cartId);
			System.out.println("ABHI Quote Item List Count= "+quoteItemsList.size());
		}
		
		checkoutQuote = this.checkoutQuoteService.getCheckoutQuoteById(cartId);
		for(int i=0; i<quoteItemsList.size(); i++) {
			CheckoutQuoteItem currentQuoteItemToDelete = quoteItemsList.get(i);
			
			checkoutQuote.setItemsCount(checkoutQuote.getItemsCount()-1);
			checkoutQuote.setItemsQuantity(checkoutQuote.getItemsQuantity()-currentQuoteItemToDelete.getQuantity());
			checkoutQuote.setBaseSubtotal(checkoutQuote.getBaseSubtotal()-currentQuoteItemToDelete.getBaseRowTotal());
			checkoutQuote.setSubtotal(checkoutQuote.getSubtotal()-currentQuoteItemToDelete.getRowTotal());
			this.checkoutQuoteItemService.removeCheckoutQuoteItem(currentQuoteItemToDelete.getId());
		}
		checkoutQuote.setBaseGrandTotal(checkoutQuote.getBaseSubtotal()+50);
		checkoutQuote.setGrandTotal(checkoutQuote.getSubtotal()+50);
		checkoutQuote.setUpdatedAt(Utilities.getCurrentDateTime());
		System.out.println("Abhi checkoutquote after = "+checkoutQuote.toString());
		
		int returnCartId = cartId;
		if (productId == -1) {
			this.checkoutQuoteService.removeCheckoutQuote(checkoutQuote.getId());
			returnCartId = -1;
		}
		else {
			this.checkoutQuoteService.addCheckoutQuote(checkoutQuote);
		}
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cartId", returnCartId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
		
	}
	
	@RequestMapping(value = "/quote/addaddress", method = RequestMethod.GET)
	public @ResponseBody String addAddress(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response){
		
		int cartId = -1;
		JSONObject user = null;
		JSONObject billing = null;
		JSONObject shipping = null;
		JSONObject country = null;
		String checkoutMethod = null;
		System.out.println("JVoidQuteController.addAddress(  jsonParams  ) ===============> "+jsonParams.toString());
		
		try {
			cartId = jsonParams.getInt("cartId");
			user = jsonParams.getJSONObject("user");
			billing = jsonParams.getJSONObject("billing");
			country = billing.getJSONObject("country");
			shipping = jsonParams.getJSONObject("shipping");
			checkoutMethod = jsonParams.getString("checkoutMethod");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		CheckoutQuote checkoutQuote = this.checkoutQuoteService.getCheckoutQuoteById(cartId);
		checkoutQuote.setCheckoutMethod(checkoutMethod);
		checkoutQuote.setUpdatedAt(Utilities.getCurrentDateTime());
		this.checkoutQuoteService.updateCheckoutQuote(checkoutQuote);
		
		try {
			CheckoutQuoteAddress checkoutQuoteAddress = new CheckoutQuoteAddress();
			checkoutQuoteAddress.setQuoteId(cartId);
			
			checkoutQuoteAddress.setFirstname(user.getString("firstName"));
			checkoutQuoteAddress.setLastname(user.getString("lastName"));
			checkoutQuoteAddress.setCompany(user.getString("company"));
			checkoutQuoteAddress.setEmail(user.getString("email"));
			
			checkoutQuoteAddress.setAddressType("billing");
			checkoutQuoteAddress.setStreet(billing.getString("address")+","+billing.getString("address2"));
			checkoutQuoteAddress.setCity(billing.getString("city")+","+billing.getString("state"));
			checkoutQuoteAddress.setCountryId(country.getString("code"));
			checkoutQuoteAddress.setPostcode(billing.getString("zip"));
			checkoutQuoteAddress.setTelephone(billing.getString("phone"));
			checkoutQuoteAddress.setFax(billing.getString("fax"));
			
			checkoutQuoteAddress.setCreatedAt(Utilities.getCurrentDateTime());
			checkoutQuoteAddress.setUpdatedAt(Utilities.getCurrentDateTime());
			
			this.checkoutQuoteAddressService.addCheckoutQuoteAddress(checkoutQuoteAddress);
			
			checkoutQuoteAddress = null;
			checkoutQuoteAddress = new CheckoutQuoteAddress();
			checkoutQuoteAddress.setQuoteId(cartId);
			
			checkoutQuoteAddress.setFirstname(user.getString("firstName"));
			checkoutQuoteAddress.setLastname(user.getString("lastName"));
			checkoutQuoteAddress.setCompany(user.getString("company"));
			checkoutQuoteAddress.setEmail(user.getString("email"));
			
			checkoutQuoteAddress.setAddressType("shipping");
			checkoutQuoteAddress.setStreet(shipping.getString("address")+","+shipping.getString("address2"));
			checkoutQuoteAddress.setCity(shipping.getString("city")+","+shipping.getString("state"));
			checkoutQuoteAddress.setCountryId(country.getString("code"));
			checkoutQuoteAddress.setPostcode(shipping.getString("zip"));
			checkoutQuoteAddress.setTelephone(shipping.getString("phone"));
			checkoutQuoteAddress.setFax(shipping.getString("fax"));
			
			checkoutQuoteAddress.setCreatedAt(Utilities.getCurrentDateTime());
			checkoutQuoteAddress.setUpdatedAt(Utilities.getCurrentDateTime());
			
			this.checkoutQuoteAddressService.addCheckoutQuoteAddress(checkoutQuoteAddress);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cartId", cartId);
			jsonObj.put("result", "Success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// slow query execution inserting into customer master cart
		//insertIntoCheckOutAddress();
		getCustomerDetails();
		return jsonObj.toString();
	}
	
	@RequestMapping(value = "/quote/getaddress", method = RequestMethod.GET)
	public @ResponseBody String getAddress(@RequestParam(required = false, value = "callback") String callback,
			@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response) throws Exception{
		
		int cartId = -1;
		JSONObject user = null;
		JSONObject billing = null;
		JSONObject shipping = null;
		JSONObject country = null;
		String checkoutMethod = null;
		try {
			cartId = jsonParams.getInt("cartId");
			user = jsonParams.getJSONObject("user");
			billing = jsonParams.getJSONObject("billing");
			country = billing.getJSONObject("country");
			shipping = jsonParams.getJSONObject("shipping");
			checkoutMethod = jsonParams.getString("checkoutMethod");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		CheckoutQuote checkoutQuote = this.checkoutQuoteService.getCheckoutQuoteById(cartId);
		checkoutQuote.setCheckoutMethod(checkoutMethod);
		checkoutQuote.setUpdatedAt(Utilities.getCurrentDateTime());
		this.checkoutQuoteService.updateCheckoutQuote(checkoutQuote);
		
		
		try {
			CheckoutQuoteAddress checkoutQuoteAddress = new CheckoutQuoteAddress();
			checkoutQuoteAddress.setQuoteId(cartId);
			
			checkoutQuoteAddress.setFirstname(user.getString("firstName"));
			checkoutQuoteAddress.setLastname(user.getString("lastName"));
			checkoutQuoteAddress.setCompany(user.getString("company"));
			checkoutQuoteAddress.setEmail(user.getString("email"));
			
			checkoutQuoteAddress.setAddressType("billing");
			checkoutQuoteAddress.setStreet(billing.getString("address")+","+billing.getString("address2"));
			checkoutQuoteAddress.setCity(billing.getString("city")+","+billing.getString("state"));
			checkoutQuoteAddress.setCountryId(country.getString("code"));
			checkoutQuoteAddress.setPostcode(billing.getString("zip"));
			checkoutQuoteAddress.setTelephone(billing.getString("phone"));
			checkoutQuoteAddress.setFax(billing.getString("fax"));
			
			checkoutQuoteAddress.setCreatedAt(Utilities.getCurrentDateTime());
			checkoutQuoteAddress.setUpdatedAt(Utilities.getCurrentDateTime());
			
			this.checkoutQuoteAddressService.addCheckoutQuoteAddress(checkoutQuoteAddress);
			
			checkoutQuoteAddress = null;
			checkoutQuoteAddress = new CheckoutQuoteAddress();
			checkoutQuoteAddress.setQuoteId(cartId);
			
			checkoutQuoteAddress.setFirstname(user.getString("firstName"));
			checkoutQuoteAddress.setLastname(user.getString("lastName"));
			checkoutQuoteAddress.setCompany(user.getString("company"));
			checkoutQuoteAddress.setEmail(user.getString("email"));
			
			checkoutQuoteAddress.setAddressType("shipping");
			checkoutQuoteAddress.setStreet(shipping.getString("address")+","+shipping.getString("address2"));
			checkoutQuoteAddress.setCity(shipping.getString("city")+","+shipping.getString("state"));
			checkoutQuoteAddress.setCountryId(country.getString("code"));
			checkoutQuoteAddress.setPostcode(shipping.getString("zip"));
			checkoutQuoteAddress.setTelephone(shipping.getString("phone"));
			checkoutQuoteAddress.setFax(shipping.getString("fax"));
			
			checkoutQuoteAddress.setCreatedAt(Utilities.getCurrentDateTime());
			checkoutQuoteAddress.setUpdatedAt(Utilities.getCurrentDateTime());
			
			this.checkoutQuoteAddressService.addCheckoutQuoteAddress(checkoutQuoteAddress);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cartId", cartId);
			jsonObj.put("result", "Success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// slow query execution inserting into customer master cart
		//insertIntoCheckOutAddress();
		String resultString = "";
		try {
			resultString = getCustomerDetailsForPayment(false);
		}
		catch(Exception e) {
			//throw e;
			//return e;
		}
		
		/*if(resultString!=null) {
			try {
				jsonObj.put("result", resultString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		return jsonObj.toString();
	}
	
	// N+1 issue
		@RequestMapping(value = "/quote/devices", method = RequestMethod.GET)
		public @ResponseBody String getAllDevices(@RequestParam(required = false, value = "callback") String callback,
				@RequestParam(required = false, value = "params") JSONObject jsonParams, HttpServletResponse response) throws Exception{
			
			List deviceList = null;
			try {
				deviceList = mobileVendorService.getMobileDetails();
				if(deviceList!=null && deviceList.size() > 0) {
					System.out.println(" deviceList Size():"+deviceList.size());
				}
				else {
					System.out.println(" deviceList is Null or Empty !!"+deviceList);
					deviceList = new ArrayList();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			JSONObject jsonObj = new JSONObject();
			try {
				//jsonObj.put("cartId", cartId);
				jsonObj.put("result", "Success");
				jsonObj.put("list", deviceList);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObj.toString();
		}
	
	// Slow query based on data available in checkout_quote_address for each row it will sleep for 10ms 
	private void insertIntoCheckOutAddress() {
		Connection connection = DatabaseConnection.getInstance();
		String cart_order_address = "SELECT id, quote_id, created_at, updated_at, customer_address_id, region_id, customer_id, fax, region, postcode, lastname, street, city, email, telephone, country_id, firstname, address_type, prefix, middlename, suffix, company FROM checkout_quote_address";
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(cart_order_address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Slow query based on data available in customer_master for each row it will sleep for 3.2 sec
	private void getCustomerDetails() {
		Connection connection = DatabaseConnection.getInstance();
		System.out.println("QuoteController.getCustomerDetails ( Connection Object ) "+connection);
		String customerDetailsQuery = "SELECT id, customer_group, prefix, first_name, middle_name, last_name, email, date_of_birth, tax_number, gender, password, company, street_address1, street_address2, street_address3, city, country, state, postal_code, phone, fax FROM customer_master"; 
		try {
			Statement statement = connection.createStatement();
			System.out.println("QuoteController.getCustomerDetails ( Statement Object ) "+statement);
			statement.executeQuery(customerDetailsQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Slow query based on data available in customer_master for each row it will sleep for 3.2 sec
		private String getCustomerDetailsForPayment(boolean flag) throws Exception {
			String sStackTrace = "";
			Connection connection = DatabaseConnection.getInstance(flag);
			System.out.println("QuoteController.getCustomerDetailsForPayment ( Connection Object ) "+connection);
			String customerDetailsQuery = "SELECT id, customer_group, prefix, first_name, middle_name, last_name, email, date_of_birth, tax_number, gender, password, company, street_address1, street_address2, street_address3, city, country, state, postal_code, phone, fax FROM customer_master"; 
			try {
				//Thread.sleep(50L);
			}
			catch(Exception e) {}
			//try {
				Statement statement = connection.createStatement();
				System.out.println("QuoteController.getCustomerDetailsForPayment ( Statement Object ) "+statement);
				statement.executeQuery(customerDetailsQuery);
			/*} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				sStackTrace = sw.toString(); // stack trace as a string
			}*/
			return sStackTrace;
		}
}
