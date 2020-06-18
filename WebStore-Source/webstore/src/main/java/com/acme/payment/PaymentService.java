package com.acme.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentService extends Thread {
	
	private String paymentServiceURL = null;
	public PaymentService(String paymentServiceURL) {
		this.paymentServiceURL = paymentServiceURL;
		this.setDaemon(true);
	}
	
	public void run() {
		processPaymentRequest(paymentServiceURL);
		try {
			Thread.sleep(3000L);
		}
		catch(Exception e) {
		}
	}
	
	public String processPaymentRequest(String paymentUrl) {
		URL url = null;
		BufferedReader reader = null;
		StringBuilder stringBuilder = null;
		try {
			url = new URL(paymentUrl);
			// url = new
			// URL("http://payment.apm.eginnovations.com:9190/Paypal_Processing/AuthenticateCard.jsp");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(10 * 1000);
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			stringBuilder = new StringBuilder("not able to process the request at this time : " + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}

}
