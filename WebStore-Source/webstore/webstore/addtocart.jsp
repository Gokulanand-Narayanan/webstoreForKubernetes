<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="org.json.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="org.springframework.http.HttpEntity"%>
<%@ page import="org.springframework.http.HttpHeaders"%>
<%@ page import="org.springframework.http.HttpMethod"%>
<%@ page import="org.springframework.http.MediaType"%>
<%@ page import="org.springframework.web.client.RestTemplate"%>
<%@ page import="org.springframework.web.util.UriComponentsBuilder" %>

<%@ page import="com.webstore.core.uriconstants.*" %>




<!DOCTYPE html>
<html><head><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}</style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>WebStore</title>

    <!--link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.0-beta.1/angular.min.js"></script>
  	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.0-beta.1/angular-sanitize.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script-->
    
    <link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="shortcut icon" href="">
  	<script src="lib/jquery.min.js"></script>
  	<script src="lib/bootstrap.min.js"></script>
   <!--  <script src="lib/angular.min.js"></script>
    <script src="lib/angular-sanitize.js"></script>
    <script src="lib/angular-route.min.js"></script>
    -->
    <link rel="stylesheet" href="css/styles.css">


    <!-- <script type="application/javascript" src="js/controller.js"></script> -->
  </head>
<%!
	/* String productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
	String PRODUCT_SERVER_URI = productDomainUri + "/products";
	String GET_PRODUCT = "/catalog/product";
	
	String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	String QUOTE_SERVER_URI = quoteDomainUri + "/cart";
	String ADD_PRODUCT_TO_CART = "/quote/add"; */
	
	public int addtocart(int productId , HttpSession sessionObj){
		int result = -1;
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			Integer cartIdObj = (Integer)sessionObj.getAttribute("cartId");
			int cartId = -1;
			if(cartIdObj != null){
				cartId = cartIdObj.intValue();
			}
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("cartId", cartId);
				jsonObj.put("productId", productId);
				jsonObj.put("attributeId", 1);
				jsonObj.put("quantity", 1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("param jsonObj=>"+jsonObj.toString());
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI+URIConstants.ADD_PRODUCT_TO_CART)
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
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}


%>
<%

	boolean flag = false;
	String productID = request.getParameter("productId");
	int product_id = -99;
	if(productID == null || productID.length() <= 0){
		response.sendRedirect("home.jsp?feedback=provideid");
	}
	product_id = Integer.parseInt(productID);
	// ---------------
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		JSONObject jsonObj = new JSONObject();    
		try {
			jsonObj.put("id", product_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.PRODUCT_SERVER_URI+URIConstants.GET_PRODUCT).queryParam("params", jsonObj);	
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		
		JSONObject returnJsonObj = null;
		try {
			returnJsonObj = new JSONObject(returnString.getBody());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//----
		if(returnJsonObj == null){
			response.sendRedirect("home.jsp?feedback=invalidid");
		}
		
		// out.println("returnJsonObj :"+returnJsonObj.toString());
		JSONArray productJArr = returnJsonObj.getJSONArray("products");
		JSONObject productJObj = productJArr.getJSONObject(0);

		String productName = productJObj.getString("name");
		String description = productJObj.getString("description");
		String country = productJObj.getString("country");
		String imageName = productJObj.getString("image");
		String visibilityString = productJObj.getString("visibility");
		int stock = productJObj.getInt("stock");
		int price = productJObj.getInt("price");
		String status = productJObj.getString("status");
		String skuString = productJObj.getString("sku");
		String urlKeyString = productJObj.getString("urlKey");
		String type = productJObj.getString("type");
		if(type == null || type.equalsIgnoreCase("null")) type = "Product";
		
		int cartId = addtocart(product_id, session);
		if(cartId >= 0){
			session.setAttribute("cartId", cartId);
		}
	
%>
  
  <body class="ng-scope" style="">
  	<div class="container">
		<jsp:include page="header.jsp"/>
		
		<div class="col-md-12">
		  	<!-- ngView --><div ng-view="" class="ng-scope">


<div class="row"  style="margin:20px 0px;">
	<div class="col-md-5">
		<img ng-src="image/<%=imageName%>" alt="image" style="width:100%" src="image/<%=imageName%>">
	</div>

	<div class="col-md-5">
		<h3 class="ng-binding"><%=productName%></h3>
		<p class="ng-binding"><%=description%></p>
		<p>
			<strong>Item Code : </strong><span class="label label-warning ng-binding"><%=skuString%></span>
		</p>
		<p class="ng-binding">
			<strong>Manufacturer</strong> : <%=country%>
		</p>
		<p class="ng-binding">
			<strong>Condition</strong> : <%=status%>
		</p>
		<p class="ng-binding">
			<strong>Availble units in stock </strong> : <%=stock%>
		</p>
		<h4 class="ng-binding"><%=price%> USD</h4>
		<p>
			<span class="text-success glyphicon glyphicon-ok btn btn-large"></span> <b class="text-success">Added To Cart </b>&nbsp;&nbsp;
			<a href="viewcart.jsp" class="btn btn-success">
				<span class="glyphicon-hand-right glyphicon"></span> View Cart
			</a> 
			<a href="home.jsp" class="btn btn-default">
				<span class="glyphicon-hand-left glyphicon"></span> back
			</a>
			<input type="hidden" value="<%=cartId%>" name="cardId">
		</p>

	</div>
</div></div>
		</div>
		<div style="clear:both"></div>
		<jsp:include page="footer.jsp"/>
	</div>
</body></html>