<%@ page import="org.json.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="org.springframework.http.HttpEntity"%>
<%@ page import="org.springframework.http.HttpHeaders"%>
<%@ page import="org.springframework.http.HttpMethod"%>
<%@ page import="org.springframework.http.MediaType"%>
<%@ page import="org.springframework.web.client.RestTemplate"%>
<%@ page import="org.springframework.web.util.UriComponentsBuilder" %>
<%@ page import="com.webstore.core.uriconstants.*" %>


<%
	/*String DELETE_CART = "/quote/delete";
	
	String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	String QUOTE_SERVER_URI = quoteDomainUri + "/cart";*/

	
	String productIdStr = request.getParameter("productId");
	String redirectto =  request.getParameter("redirectto");
	boolean emptyCart = false;
	int productId = -1;
	if(productIdStr != null){
		try{
			productId = Integer.parseInt(productIdStr);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	if(productId == -1){
		emptyCart = true;
	}
		int result = -1;
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			Integer cartIdObj = (Integer)session.getAttribute("cartId");
			int cartId = -1;
			if(cartIdObj != null){
				cartId = cartIdObj.intValue();
			}
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("cartId", cartId);
				jsonObj.put("productId", productId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("param jsonObj=>"+jsonObj.toString());
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI + URIConstants.DELETE_CART)
					.queryParam("params", jsonObj);	
			HttpEntity<?> entity = new HttpEntity<>(headers);
			HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
			JSONObject returnJsonObj = null;
			try {
				returnJsonObj = new JSONObject(returnString.getBody());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(returnJsonObj!=null){
				result = returnJsonObj.getInt("cartId");
				if(emptyCart){
					session.setAttribute("cartId", -1);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(redirectto != null){
			 response.sendRedirect(redirectto);
		}
		else{
			 response.sendRedirect("viewcart.jsp");
		}

%>