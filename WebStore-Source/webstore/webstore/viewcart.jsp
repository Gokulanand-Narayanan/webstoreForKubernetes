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
 <!--    <script src="lib/angular.min.js"></script>
    <script src="lib/angular-sanitize.js"></script>
    <script src="lib/angular-route.min.js"></script>
    --> 
    <link rel="stylesheet" href="css/styles.css">

   <!--  <script type="application/javascript" src="js/controller.js"></script> -->
  </head>
  
  <body ng-app="jvoid" class="ng-scope">
  	<div class="container">
		
		<jsp:include page="header.jsp"/>
		
		<div class="col-md-12">
		  	<!-- ngView --><div ng-view="" class="ng-scope"><!--
 * jVoiDView cart
 * 
 * @author Shajir <shajir@schogini.com>
 * @version 1.0
 *
 -->
<br>
<br>
<div class="">
	<!-- <h1>{{title}}</h1> -->
	<h1 class="ng-binding">Your Cart</h1> 
</div>

<%  
/*
System.out.println("printing errors");
 Object a = null;
a.toString(); 
*/
  
 %>

	
<%
	/*String productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
	String PRODUCT_SERVER_URI = productDomainUri + "/products";
	String GET_PRODUCT = "/catalog/product";
	String GET_CART = "/quote/viewcart";
	
	String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	String QUOTE_SERVER_URI = quoteDomainUri + "/cart";
	String ADD_PRODUCT_TO_CART = "/quote/add";*/
	
	Integer cartIdObj = (Integer)session.getAttribute("cartId");
	int cartId = -1;
	if(cartIdObj != null){
		cartId = cartIdObj.intValue();
	}
	JSONObject returnJsonObj = null;
	if(cartId >= 0){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);


		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cartId", cartId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI + URIConstants.GET_CART)
				.queryParam("params", jsonObj);	
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		
		try {
			returnJsonObj = new JSONObject(returnString.getBody());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//  out.println(returnJsonObj);
	 
	 int size = 0;
	 int total = 0;
	 JSONArray itemJArr = new JSONArray();
	if(returnJsonObj != null){
		session.setAttribute("cartItems", returnJsonObj);
		total = returnJsonObj.getInt("total");
		itemJArr = returnJsonObj.getJSONArray("items");
		size = itemJArr.length();
	}
%>
<div>
	<% if(size > 0){
	%>
	<div style="margin-botton:10px">
		<a class="btn btn-danger pull-left" href="removecart.jsp?productId=-1"> 
			<span class="glyphicon glyphicon-remove-sign"></span> Clear Cart
		</a> 
		<a href="billingdetails.jsp" class="btn btn-success pull-right" ng-click="checkoutCart()" ng-disabled="emptyCart"> 
			<span class="glyphicon-shopping-cart glyphicon"></span> Check out
		</a>
	</div>
	<%}%>
	<div style="clear:both"></div>
	<table class="table table-hover" style="margin-top:10px;background: #fff;">
		<tbody><tr>
			<th>Product</th>
			<th>Unit price</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Action</th>
		</tr>
		<!-- ngRepeat: item in items -->
	
<%
		for(int i=0;i<size;i++){
			JSONObject itemObj = itemJArr.getJSONObject(i);
			int itemId = itemObj.getInt("id");
			int productId = itemObj.getInt("productId");
			String itemName = itemObj.getString("name");
			int basePrice = itemObj.getInt("basePrice");
			int quantity = itemObj.getInt("quantity");
			int baseRowTotal = itemObj.getInt("baseRowTotal");
			
%>
		
		<tr ng-repeat="item in items">
			<td><%=itemName%></td>
			<td><%=basePrice%></td>
			<td><%=quantity%></td>
			<td><%=baseRowTotal%></td>
			<td><a class="label label-danger" href="removecart.jsp?productId=<%=productId%>"> 
				<span class="glyphicon glyphicon-remove" /></span> Remove
			</a></td>
		</tr>
		<%}%>		
		<tr>
			<th></th>
			<th></th>
			<th>Grand Total</th>
			<th><%=total%></th>
			<th></th>
		</tr>
			
	</tbody></table>
	
	<a href="home.jsp" class="btn btn-default">
		<span class="glyphicon-hand-left glyphicon"></span> Continue shopping
	</a>
</div></div>
		</div>
		
		<!--<div class="footer pull-right">
			<p>&copy; eG Java APM 2018</p>
		</div>-->
		<jsp:include page="footer.jsp"/>
	</div>
    
</body>
</html>
