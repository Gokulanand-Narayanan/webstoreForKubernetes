package com.webstore.core.engine.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class EngineCallerService {
	
	public static String processCart(String GET_URL,String USER_AGENT) throws IOException {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(245);stop>System.nanoTime();) {
			
		}
		System.out.println("EngineCallerService.processCart took "+(System.currentTimeMillis()-startTime));
		StringBuffer result = new StringBuffer();
		HttpURLConnection con = null;
		URL obj = null;
		try {
			obj = new URL(GET_URL);
			con = (HttpURLConnection) obj.openConnection();
			HttpURLConnection.setFollowRedirects(false);
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			result.append("webAuthenticateController Get Response Code :: ").append(responseCode).append("<br>");
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result.append(response.toString()).append("<br>");
				System.out.println("EngineCallerService processCart : "+result.toString());
			} else {
				System.out.println("GET request not worked for : "+GET_URL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.disconnect();
			con=null;
		}
		return result.toString();
	}

	public static String processAutrentication(String POST_URL,String USER_AGENT,String POST_PARAMS) throws IOException {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(245);stop>System.nanoTime();) {
			
		}
		System.out.println("EngineCallerService.processAutrentication took "+(System.currentTimeMillis()-startTime));
		StringBuffer result = new StringBuffer();
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = con.getResponseCode();
		result.append("webCartController Response Code :: ").append(responseCode).append("<br>");

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			result.append(response.toString()).append("<br>");
			System.out.println("EngineCallerService processAutrentication : "+result.toString());
		} else {
			System.out.println("POST request not worked for : "+POST_URL+" with params :"+POST_PARAMS);
		}
		return result.toString();
	}
}
