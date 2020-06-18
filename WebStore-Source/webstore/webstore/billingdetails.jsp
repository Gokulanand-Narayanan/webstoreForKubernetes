<%@ page import="org.json.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="org.springframework.http.HttpEntity"%>
<%@ page import="org.springframework.http.HttpHeaders"%>
<%@ page import="org.springframework.http.HttpMethod"%>
<%@ page import="org.springframework.http.MediaType"%>
<%@ page import="org.springframework.web.client.RestTemplate"%>
<%@ page import="org.springframework.web.util.UriComponentsBuilder" %>


<!DOCTYPE HTML>
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
    <!-- <script src="lib/angular.min.js"></script>
    <script src="lib/angular-sanitize.js"></script>
    <script src="lib/angular-route.min.js"></script>
    -->
    <link rel="stylesheet" href="css/styles.css">


  <!--  <script type="application/javascript" src="js/controller.js"></script> -->
  <style>
  .row {
    margin-right: 0px;
    margin-left: 0px;
	}
	input[type=radio], input[type=checkbox] {
    margin: 0px 0 0; 
  
    line-height: normal;
}
  </style>
  </head>
  
  <body  class="ng-scope" style="">
  	<div class="container">
			<jsp:include page="header.jsp"/>
		
		<div class="col-md-12" style="background:#fff;">
		  	<!-- ngView --><div ng-view="" class="ng-scope">


<%	JSONObject customerObj =(JSONObject)request.getSession().getAttribute("CustomerObj");
	// out.println("CuST :"+customerObj);
	
	if(customerObj == null || customerObj.equals("null")){
		response.sendRedirect("signin.jsp");
		return;
	}

	String lastName = customerObj.getString("lastName");
	if(lastName == null || lastName.equalsIgnoreCase("null") ) lastName = "";
	String country = customerObj.getString("country");
	country = country.replace("\\", "");
	JSONObject countryJObj = new JSONObject(country);
	country = countryJObj.getString("name");
	String gender = customerObj.getString("gender");
	String city = customerObj.getString("city");
	String prefix = customerObj.getString("prefix");
	String postalCode = customerObj.getString("postalCode");
	String streetAddress1 = customerObj.getString("streetAddress1");
	if(streetAddress1 == null || streetAddress1.equalsIgnoreCase("null") ) streetAddress1 = "";
	String taxNumber = customerObj.getString("taxNumber");
	String streetAddress2 = customerObj.getString("streetAddress2");
	if(streetAddress2 == null || streetAddress2.equalsIgnoreCase("null") ) streetAddress2 = "";
	String dateOfBirth = customerObj.getString("dateOfBirth");
	String streetAddress3 = customerObj.getString("streetAddress3");
	if(streetAddress3 == null || streetAddress3.equalsIgnoreCase("null") ) streetAddress3 = "";
	String firstName = customerObj.getString("firstName");
	String phone = customerObj.getString("phone");
	String middleName = customerObj.getString("middleName");
	int id = customerObj.getInt("id");
	String state = customerObj.getString("state");
	state = state.replace("\\", "");
	JSONObject stateJObj = new JSONObject(state);
	state = stateJObj.getString("name");
	String fax = customerObj.getString("fax");
	String email = customerObj.getString("email");
	int customerGroup = customerObj.getInt("customerGroup");
	String company = customerObj.getString("company");
	JSONObject cartItems = (JSONObject) session.getAttribute("cartItems");
	
	Integer cartIdObj = (Integer)session.getAttribute("cartId");
	int cartId = -1;
	if(cartIdObj != null){
		cartId = cartIdObj.intValue();
	}
	
	JSONObject userJObj = new JSONObject();
	userJObj.put("id", id);
	userJObj.put("firstName", firstName);
	userJObj.put("lastName", lastName );
	userJObj.put("company", company );
	userJObj.put("email", email);
	
	
	JSONObject billingJObj = new JSONObject();
	billingJObj.put("address", streetAddress1);
	billingJObj.put("address2", streetAddress2);
	billingJObj.put("city", city);
	billingJObj.put("state", stateJObj);
	billingJObj.put("country", countryJObj);
	billingJObj.put("zip", postalCode);
	billingJObj.put("phone", phone);
	billingJObj.put("fax", fax);
	
	JSONObject result = new JSONObject();
	result.put("cartId", cartId);
	result.put("cart", cartItems);
	result.put("user", userJObj);
	result.put("billing", billingJObj);
	result.put("shipping", billingJObj);
	result.put("checkoutMethod", "COD");

	session.removeAttribute("cartItems");
	session.setAttribute("billingDetails", result);
%>
<div style="margin-top:10px">
	<form name="signup_form" action="paymentprocessing.jsp" method="POST">
		<fieldset>
			<legend>Checkout Details</legend>
			<div class="row">
				<div class="large-12 columns">
					<span><%=firstName%> <%=lastName%></span><br>
					<span><%=email%></span>
		    	</div>
		    </div><br>
		    
		    <legend>Billing Address</legend>
			<div class="row">
				<div class="large-12 columns">
					<span><%=streetAddress1%></span><br>
					<span><%=streetAddress2%>, <%=streetAddress3%></span><br>
					<span><%=city%></span><br>
					<span ng-show="isSStatesDisplay"><%=state%></span><br ng-show="isSStatesDisplay">
					<span><%=country%></span><br>
					<span><%=postalCode%></span><br>
					<span>Phone: <%=phone%></span><br>
					<span>Fax: <%=fax%></span><br>
		    	</div>
		    </div><br>
		    
		    <legend>Shipping Address</legend>
		    <div class="row">
				<div class="large-12 columns">
		    		<span>Same as Billing Address</span> <input type="checkbox" ng-change='onCHeckBoxAction(address)' ng-model="checked" checked="checked">
		    	</div>
		    </div><br>
		    <div class="row" ng-show="checked">
				<div class="large-12 columns">
					<span><%=streetAddress1%></span><br>
					<span><%=streetAddress2%>, <%=streetAddress3%></span><br>
					<span><%=city%></span><br>
					<span ng-show="isSStatesDisplay"><%=state%></span><br ng-show="isSStatesDisplay">
					<span><%=country%></span><br>
					<span><%=postalCode%></span><br>
					<span>Phone: <%=phone%></span><br>
					<span>Fax: <%=fax%></span><br>
				</div>
		    </div><br>	
		    
		   
		    <legend>Select Payment Method</legend>
		    <div class="row">
				<div class="large-12 columns">
					<input type="radio" ng-model="payment" value="COD" checked="checked">  Cash On Delivery <br/>
  					<input type="radio" ng-model="payment" value="DC"> Debit Card <br/>
  					<input type="radio" ng-model="payment" value="CC"> Credit Card <br/>
  					<input type="radio" ng-model="payment" value="NB"> Net Banking <br/>
		    	</div>
		    </div>
		    <br><br>
		    
		    <legend>Your comments</legend>
		    <div class="row">
				<div class="large-12 columns">
					<textarea ng-model="comment" placeholder="Your Comment" style='width:550px'></textarea>
		    	</div>
		    </div>
		    <br><br>
		    <input type="hidden" name="productId" value="-1"/>
		    <!--<input type="hidden" name="redirectto" value="home.jsp"/> -->
		    <button type="submit" class="btn btn-success radius" ng-disabled="">Make Payment</button>
	    </fieldset>
  	</form>
</div>

		
		
	</div>
	
	</div>
   
		<jsp:include page="footer.jsp"/>
</body></html>