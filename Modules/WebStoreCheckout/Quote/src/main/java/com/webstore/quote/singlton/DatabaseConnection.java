package com.webstore.quote.singlton;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DatabaseConnection{
	 // static variable single_instance of type Singleton 
    private static Connection connectionObject = null; 
    private static String jdbcURL;
    private static String userName="root";
    private static String password="root";
    private static String driverClass = "com.mysql.jdbc.Driver";

    private DatabaseConnection() {
		
	}
    
    public static Connection getInstance(boolean error) {
    	Connection connection = null;
    	return connection;
    }

    public static Connection getInstance() 
    { 
    	try{
    		if (connectionObject == null || connectionObject.isClosed()) {
    			synchronized (DatabaseConnection .class) {
                    if (connectionObject == null) {
                    	getConfigData();
                    	connectionObject = getDBConnection();
                    }
                }
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        return connectionObject;
    }
    
    private static void  getConfigData() {
    	Properties prop = new Properties();
    	InputStream input = null;

    	try {
    		input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
    		// load a properties file
    		prop.load(input);
    		// get the property value and print it out
    		driverClass =  prop.getProperty("driverClassName");
    		jdbcURL = prop.getProperty("url");
    		userName = prop.getProperty("username");
    		password = prop.getProperty("password");

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
    }
    
    private static Connection getDBConnection() {
      	try{
			Class.forName(driverClass);
			connectionObject = DriverManager.getConnection(jdbcURL,userName,password);
		}
		catch(Exception e){
			System.out.println("Exception occured while creating Connection with JDBC URL :"+jdbcURL);
			e.printStackTrace();
		}
    	return connectionObject;
    }
}
