<%@ page import = "java.util.ArrayList"%>
<%@ page import = "org.json.JSONException"%>
<%@ page import = "org.json.JSONObject"%>
<%@ page import = "org.springframework.http.HttpEntity"%>
<%@ page import = "org.springframework.http.HttpHeaders"%>
<%@ page import = "org.springframework.http.HttpMethod"%>
<%@ page import = "org.springframework.http.MediaType"%>
<%@ page import = "org.springframework.web.client.RestTemplate"%>
<%@ page import = "org.springframework.web.util.UriComponentsBuilder"%>
<%@ page import = "com.webstore.core.uriconstants.*"%>

<%
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		//JSONObject jsonParams = new JSONObject();
		

	String firstName = request.getParameter("firstName");
	String email = request.getParameter("email");
	String password = request.getParameter("password");

	JSONObject countryJObj = new JSONObject();
	countryJObj.put("name", "United Kingdom");
	countryJObj.put("code", "GB");
	countryJObj.put("filename", "united-kingdom");
	
	JSONObject stateJObj = new JSONObject();
	stateJObj.put("name", "London, City of");
	stateJObj.put("code", "GB-LND");

		JSONObject jObj = new JSONObject();
		try {
			jObj.put("id", -1);
			jObj.put("city", "London");
			jObj.put("gender", "male");
			jObj.put("company", "Jax-London");
			jObj.put("customerGroup", 3);
			jObj.put("dateOfBirth", "09-10-1982");
			jObj.put("email", email);
			jObj.put("fax", "1234567890");
			jObj.put("firstName", firstName);
			jObj.put("middleName", "");
			jObj.put("lastName", "");
			jObj.put("password", "pwd123");
			jObj.put("phone", "9999999999");
			jObj.put("taxNumber", "TAX1234");
			jObj.put("postalCode", "N1 0QH");
			jObj.put("prefix", "Mr.");
			jObj.put("state", stateJObj);
			jObj.put("country", countryJObj);
			jObj.put("streetAddress1", "52 Upper Street");
			jObj.put("streetAddress2", "Islington");
			jObj.put("streetAddress3", "");
			jObj.put("type", "Customer");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		out.println("Json Obj == "+jObj.toString());

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String uri = "";
		try {
			
				uri = ServerUris.CUSTOMER_SERVER_URI+URIConstants.ADD_CUSTOMER;
			
		} catch (Exception ed) {
			// TODO Auto-generated catch block
			ed.printStackTrace();
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
		        .queryParam("params", jObj);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
		JSONObject returnJsonObj = null;
		try {
			returnJsonObj = new JSONObject(returnString.getBody());

		} catch (Exception eh) {
			// TODO Auto-generated catch block
			out.println("---returnJsonObj-eheheheheh---"+eh);
			eh.printStackTrace();
		}
		out.println(returnString);
		//HttpSession hsession = request.getSession(true);
		//hsession.setAttribute("CustomerObj",jObj);
		out.println("---returnJsonObj----"+returnJsonObj);
		request.getRequestDispatcher("login.jsp?password="+firstName+"&inputEmail="+email).forward(request, response);
		//response.sendRedirect("home.jsp?password="+firstName+"&email="+email);

%>