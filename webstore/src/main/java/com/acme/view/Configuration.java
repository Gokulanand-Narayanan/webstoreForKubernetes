package com.acme.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class Configuration {
	
	private static final int counterValue = 1;
	private static final int timeValue = 3;
	private static final String propFileName = "config.properties";
	private static final String dataFileName = "data.properties";
	
	private static int increment_value = counterValue; // default
	private static int time_to_increase = timeValue; // default;
	
	static {
		updateConfigDetails();
	}
	
	public static int getIncrementValue() {
		return increment_value;
	}
	
	public static int getTimeValue() {
		return time_to_increase;
	}
	
	public static void updateConfigDetails() {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			inputStream = Configuration.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				
				String increment = prop.getProperty("increment_value");
				if(increment!=null && increment.length() > 0) {
					try{
						increment_value = Integer.parseInt(increment);
					}
					catch(Exception e) {
						increment_value = counterValue;
					}
				}
				
				String increase = prop.getProperty("time_to_increase");
				if(increase!=null && increase.length() > 0) {
					try{
						time_to_increase = Integer.parseInt(increase);
					}
					catch(Exception e) {
						time_to_increase = timeValue;
					}
				}
				
			}
			else {
				increment_value = counterValue;
				time_to_increase = timeValue; 
			}
		}
		catch(Exception e) {
			increment_value = counterValue;
			time_to_increase = timeValue; 
		}
		finally{
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
	}
	
	public static void updateDetails(int value) {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			URL url = Configuration.class.getClassLoader().getResource(dataFileName);
			//System.out.println("URL : "+url);
			
			if(url!=null) {
				String fileName = url.getFile();
				output = new FileOutputStream(fileName);
				// set the properties value
				prop.setProperty("previous_value", ""+value);
				// save properties to project root folder
				prop.store(output, null);
			}
			else {
				System.out.println(" URL is NULL ");
			}

		} catch (Exception io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int getDetails() {
		int result = 50;
		String line = null;
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Configuration.class.getClassLoader().getResourceAsStream(dataFileName);
			// load a properties file
			prop.load(input);
			line = prop.getProperty("previous_value");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//if(line!=null && line.startsWith("previous_value") && line.indexOf("=") > 0) {
		if(line!=null) {
			try {
				//line = line.substring(line.indexOf("=")+1);
				result = Integer.parseInt(line);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
