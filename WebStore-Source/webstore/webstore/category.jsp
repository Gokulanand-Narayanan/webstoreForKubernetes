<%@ page import="org.json.*"%>
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
   <!-- <script src="lib/angular.min.js"></script>
    <script src="lib/angular-sanitize.js"></script>
    <script src="lib/angular-route.min.js"></script>
    -->
    <link rel="stylesheet" href="css/styles.css">
<!-- RUM Header --> 
<script> window["egrum-start_time"] = new Date().getTime(); 
window["Site_Name"] = "c80996a3-8d14-4cd1-a799-4b30addf46fa-1538386525818"; 
window["beacon-url"] = "http://192.168.11.102:7077";</script> 
<script src="http://192.168.11.102:7077/rumcollector/egrum.js"> </script> 
<!-- RUM Header --> 

    <!-- <script type="application/javascript" src="js/controller.js"></script> -->
  </head>
  
  <body class="ng-scope">
  	<div class="container">
		<jsp:include page="header.jsp"/>
		<div class="col-md-12">
		  	<!-- ngView -->
			<div ng-view="" class="ng-scope">
			
			 <br>
			 <br>
			<div class="">
				<h1 class="ng-binding"> Categories </h1>
				<!-- <h1> {{detTitle}}</h1> --->
			</div>

<%
		/*String productDomainUri = "http://"+"products.apm.eginnovations.com"+":4040";
		String PRODUCT_SERVER_URI = productDomainUri + "/products";
		String GET_PRODUCT = "/catalog/product";
		String GET_CATEGORY = "/catalog/category";*/
		int category_id = -1;
	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		JSONObject jsonObj = new JSONObject();    
		try {
			jsonObj.put("id", category_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.PRODUCT_SERVER_URI+URIConstants.GET_CATEGORY).queryParam("params", jsonObj);	
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
		JSONArray categoryJArr = returnJsonObj.getJSONArray("categories");
		int size = categoryJArr.length();

%>
		<div class="row">
<%
		
	for(int i=0;i<size;i++){
		JSONObject categoryJObj = categoryJArr.getJSONObject(i);
		int categoryId = categoryJObj.getInt("id");
		String categoryName = categoryJObj.getString("categoryName");
		if(categoryName.length() > 16){
			categoryName = categoryName.substring(0,16);
		}
			
			
%>
			<!-- ngRepeat: category in categories -->
			<div class="col-sm-6 col-md-3 ng-scope" style="padding-bottom: 15px" ng-repeat="category in categories">
				<div class="thumbnail">
					<img src="image/C1234.jpg" alt="image" style="width:100%">
					<div class="caption">
						<h3 class="ng-binding"><%=categoryName%></h3>
						<p>Dummy Description</p>
						<p>
							<a href="home.jsp?cat=<%=categoryId%>" class="btn btn-primary"> 
							<span class="glyphicon glyphicon-info-sign"></span> Browse Products
							</a>
						</p>
					</div>
				</div>
			</div><!-- end ngRepeat: category in categories -->
<%
	}
%>
		</div>
	</div>
		
	<div class="footer pull-right">
		<p>&copy; eG Java APM 2018</p>
	</div>
</div>
</body></html>