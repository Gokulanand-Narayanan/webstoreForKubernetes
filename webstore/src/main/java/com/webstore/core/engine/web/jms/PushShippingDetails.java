package com.webstore.core.engine.web.jms;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.webstore.core.engine.web.props.EnginePropertyReader;

public class PushShippingDetails {
	
	Context namingContext = null;
	String messageCount = null;
	public PushShippingDetails(String count) {
		this.messageCount = count;
	}
	public String sendShippingDetailsQueue() {
		Properties shipPropsObj = getShipPropsObject();
		String connectionFactoryName = shipPropsObj.getProperty("DEFAULT_CONNECTION_FACTORY");
		String destinationName = shipPropsObj.getProperty("DEFAULT_DESTINATION");
		if(messageCount == null) {
			messageCount = shipPropsObj.getProperty("DEFAULT_MESSAGE_COUNT");
		}
		String textMessage = shipPropsObj.getProperty("DEFAULT_MESSAGE");
		String userNameString = shipPropsObj.getProperty("DEFAULT_USERNAME");
		String passwordString = shipPropsObj.getProperty("DEFAULT_PASSWORD");
		String initialContextFactory = shipPropsObj.getProperty("INITIAL_CONTEXT_FACTORY");
		String providerUrl = shipPropsObj.getProperty("PROVIDER_URL");
		String userName = System.getProperty("username", userNameString);
        String password = System.getProperty("password", passwordString);
        
        namingContext = getJmsContext(initialContextFactory,providerUrl,userName,password);
        JMSContext context = null;
		try {
			String connectionFactoryString = System.getProperty("connection.factory", connectionFactoryName);
	        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
	        
	        String destinationString = System.getProperty("destination", destinationName);
	        Destination destination = (Destination) namingContext.lookup(destinationString);
	        
	        int count = Integer.parseInt(System.getProperty("message.count", messageCount));
	        String content = System.getProperty("message.content", textMessage);

	        context = connectionFactory.createContext(userName, password);
	        
	        for (int i = 0; i < count; i++) {
                context.createProducer().send(destination, content);
            }
	      System.out.println(count+" : message pushed into ["+destinationString+"]");
		} catch (Exception e) {
			System.err.println("exception while sending message into queue ");
			e.printStackTrace();
		}finally {
			if (context != null) {
				context.close();
			}
		}
		return "data stored in queue";
	}

	private Context getJmsContext(String initialContextFactory, String providerUrl, String userName, String password) {
		try {
			final Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
	        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, providerUrl));
	        env.put(Context.SECURITY_PRINCIPAL, userName);
	        env.put(Context.SECURITY_CREDENTIALS, password);
	        namingContext = new InitialContext(env);
		} catch (Exception e) {
			System.err.println("exception while preparing context"+e.getMessage());
			e.printStackTrace();
		}
		return namingContext;
	}
	
	private Properties getShipPropsObject() {
		EnginePropertyReader propertyReader = new EnginePropertyReader();
		return propertyReader.getEnginePropsObj();
	}
	
	public static void main(String[] args) {
		PushShippingDetails details = new PushShippingDetails("2");
		details.sendShippingDetailsQueue();
	}
}
