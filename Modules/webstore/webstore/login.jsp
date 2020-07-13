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

		String emailString = request.getParameter("inputEmail");
		String passwordString = request.getParameter("password");

		JSONObject obj = new JSONObject();
		
		try {
			obj.put("email", emailString);
			obj.put("password", passwordString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ServerUris.CUSTOMER_SERVER_URI+URIConstants.GET_CUSTOMER_BY_EMAIL)
					.queryParam("params", obj);	
			HttpEntity<?> entity = new HttpEntity<>(headers);
			System.out.println("urlString Url = "+ UriComponentsBuilder.fromHttpUrl(ServerUris.CUSTOMER_SERVER_URI+URIConstants.GET_CUSTOMER_BY_EMAIL).queryParam("params", obj).build().toUri());
			HttpEntity<String> returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
				
			
			//out.println("------returnString-------"+returnString);
			String result = "";
			JSONObject returnJsonObj = null;
			try {
				returnJsonObj = new JSONObject(returnString.getBody());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//out.println(" returnJsonObj :"+returnJsonObj);

			HttpSession hsession = request.getSession(true);
			hsession.setAttribute("CustomerObj",returnJsonObj);
			//request.getRequestDispatcher("/home.jsp").forward(request, response);
			response.sendRedirect("home.jsp");
		
		}catch(Exception e){
			//e.printStackTrace();
			response.sendRedirect("signin.jsp?info=error");
			
		}
			

%>