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

<%
	//String DELETE_CART = "/quote/delete";
	
	//String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	//String QUOTE_SERVER_URI = quoteDomainUri + "/cart";

	
	String productIdStr = request.getParameter("productId");
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
		/*if(redirectto != null){
			 response.sendRedirect(redirectto);
		}
		else{
			 response.sendRedirect("viewcart.jsp");
		}*/
		// response.sendRedirect("success.jsp");

%>


<!DOCTYPE html>
<html><head><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}</style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>WebStore</title>

    <!-- link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
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

<%

	String orderId = request.getParameter("orderid");
	if(orderId== null) orderId="";
	
%>
  
  <body ng-app="jvoid" class="ng-scope" style="">
  	<div class="container">
		<jsp:include page="header.jsp"/>
		
		<div class="col-md-12">
		  	<!-- ngView --><div ng-view="" class="ng-scope"><!--
 * jVoiDView product
 * 
 * @author Shajir
 * @version 1.0
 *
 -->

<div class="jumbotron">
	<h2 class="ng-binding">
		Your Payment is being processed. Please wait...
	</h2 >
</div>

</div>
</div>
		
		<div class="footer pull-right">
			<p>&copy; eG Java APM 2018</p>
		</div>
	</div>
	<script>
		setTimeout(function(){
			window.location.href = "success.jsp";
		}, 3000);
	</script>
</body></html>
