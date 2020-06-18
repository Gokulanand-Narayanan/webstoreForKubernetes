package com.webstore.core.engine.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.webstore.core.uriconstants.ServerUris;
import com.webstore.core.uriconstants.URIConstants;

/**
 * Servlet implementation class PaymentGatewayController
 */
@WebServlet("/payment")
public class PaymentGatewayController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String gateway = request.getParameter("gateway").trim();
		try {
			System.out.println("gateway = " + gateway);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			JSONObject jsonParams = new JSONObject();

			jsonParams.put("gateway", gateway);

			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(ServerUris.PAYMENT_SERVER_URI + "/payment/" + URIConstants.MAKE_PAYMENT)
					.queryParam("params", jsonParams);
			
			System.out.println("Payment Url = " +builder.toString());
			
			HttpEntity<?> entity = new HttpEntity<>(headers);
			HttpEntity<String> returnString = null;
			try {
				returnString = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
			} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
				System.out.println("--------------------------->" + httpClientOrServerExc.getStatusCode()+"   "+httpClientOrServerExc.getStatusCode().is5xxServerError());
				if (httpClientOrServerExc.getStatusCode().is5xxServerError()) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().print("Http Status 500 - Internal Server Error");
				}
			}
			if (returnString != null) {
				response.getWriter().print(returnString.getBody());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
} 
