<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ page import="org.apache.activemq.ActiveMQConnectionFactory"%>

<%@ page import="javax.jms.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>
<%@ page import="org.json.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="org.springframework.http.HttpEntity"%>
<%@ page import="org.springframework.http.HttpHeaders"%>
<%@ page import="org.springframework.http.HttpMethod"%>
<%@ page import="org.springframework.http.MediaType"%>
<%@ page import="org.springframework.web.client.RestTemplate"%>
<%@ page import="org.springframework.web.util.UriComponentsBuilder" %>
<%@ page import="com.webstore.core.uriconstants.*" %>

<%!
	//String queueUrl = "tcp://localhost:61616";
	//String queueName = "shipping-queue";
	
	public String messageSender(String textMessage, String queueUrl , String  queueName)
    {
		String text = null;
    	  try {
              // Create a ConnectionFactory
              ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueUrl);

              // Create a Connection
              Connection connection = connectionFactory.createConnection();
              connection.start();

              // Create a Session
              Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

              // Create the destination (Topic or Queue)
              Destination destination = session.createQueue(queueName);

              // Create a MessageProducer from the Session to the Topic or Queue
              MessageProducer producer = session.createProducer(destination);
              producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

              // Create a messages
              text = textMessage + " : " + this.hashCode();
              
              TextMessage message = session.createTextMessage(text);
              
              // Tell the producer to send the message
                          
              producer.send(message);

              // Clean up
              session.close();
              connection.close();
          }
          catch (Exception e) {
              
              e.printStackTrace();
          }
    	  return text;
    }


%>

<%!
	public String processPaymentRequest(String paymentUrl) {
			URL url = null;
			BufferedReader reader = null;
			StringBuilder stringBuilder = null;
		   try{
			   url = new URL(paymentUrl);
			   //url = new URL("http://payment.apm.eginnovations.com:9190/Paypal_Processing/AuthenticateCard.jsp");
			   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			   connection.setRequestMethod("GET");
			   connection.setReadTimeout(10*1000);
			   connection.connect();
			   reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			   stringBuilder = new StringBuilder();
			   String line = null;
			   while ((line = reader.readLine()) != null){
				   stringBuilder.append(line + "\n");
		      }
		}
		catch (Exception e){
		  e.printStackTrace();
		  stringBuilder = new StringBuilder("not able to process the request at this time : "+e.getMessage());
		}
		finally{
		  if (reader != null){
		    try{
		      reader.close();
		    }
		    catch (IOException ioe){
		      ioe.printStackTrace();
		    }
		  }
		}
	    return stringBuilder.toString();
	}
%>

<%
	/*String DELETE_CART = "/quote/delete";
	String CHECKOUT_CART = "/quote/addaddress";
	String ADD_ORDER = "/order/addaddress";
	
	String orderDomainUri = "http://"+"orders.apm.eginnovations.com"+":6060";
	String quoteDomainUri = "http://"+"cart.apm.eginnovations.com"+":5050";
	String QUOTE_SERVER_URI = quoteDomainUri + "/cart";
	String ORDER_SERVER_URI = orderDomainUri + "/order";*/
	
	JSONObject jsonParams = (JSONObject) session.getAttribute("billingDetails");
	int orderId = 0;
	int totalAmount =0;
	String tag = "0 | 0";
	try{
		totalAmount = jsonParams.getJSONObject("cart").getInt("total");
		session.setAttribute("amount",totalAmount); 
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	try{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		// out.println("jsonParams=>"+jsonParams);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.QUOTE_SERVER_URI + URIConstants.CHECKOUT_CART)
				.queryParam("params", jsonParams.toString());	
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		
		JSONObject returnJsonObj = null;
		try {
			returnJsonObj = new JSONObject(returnString.getBody());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// out.println("returnJsonObj=>"+returnJsonObj);
		
		String result = "";
		try {
			result = returnJsonObj.getString("result");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String responseStr = "";
		if(result.equals("Success")){
			UriComponentsBuilder builder1 = UriComponentsBuilder.fromHttpUrl(ServerUris.ORDER_SERVER_URI + URIConstants.ADD_ORDER)
					.queryParam("params", jsonParams);	
			HttpEntity<?> entity1 = new HttpEntity<>(headers);
			HttpEntity<String> returnString1 = restTemplate.exchange(builder1.build().toUri(), HttpMethod.GET, entity1, String.class);
			responseStr = returnString1.getBody();
			messageSender(jsonParams.toString(), ServerUris.getActiveMQDomain(), ServerUris.getJMSQueueName());
			processPaymentRequest(ServerUris.getPaymentDomainURI()+"/Paypal_Processing/AuthenticateCard.jsp");
		}

		// out.println("ORDER =====> "+responseStr);
		
		JSONObject orderJobj = new JSONObject(responseStr);
		orderId = orderJobj.getInt("orderId");
		tag = String.valueOf("Order ID : "+orderId)+" | "+String.valueOf("Price : "+totalAmount);
		System.out.println("---tag---"+tag);
		session.setAttribute("tag",tag);
		session.setAttribute("orderId",String.valueOf(orderId));
		session.setAttribute("amount",String.valueOf(totalAmount));
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	session.removeAttribute("billingDetails");
	session.removeAttribute("cartId");

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
		
		<jsp:include page="footer.jsp"/>
	</div>
	<script>
		setTimeout(function(){
			window.location.href = "success.jsp";
		}, 3000);
	</script>
</body></html>
