package com.webstore.products.web.jms;

import java.util.Properties;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.webstore.products.web.props.CartPropertyReader;

public class SendShippingDetails {
	private static final Logger log = Logger.getLogger(SendShippingDetails.class.getName());

    // Set up all the default values
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/paymentProcessingQueue";

    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:4040";
    Context namingContext = null;
   
	public void sendDetails(String mcount) {
	        ConnectionFactory connectionFactory = null;
	        Connection connection = null;
	        Session session = null;
	        MessageProducer producer = null;
	        Destination destination = null;
	        TextMessage message = null;
	        Context context = null;
	        String messageCount = mcount;
	        Properties shipPropsObj = getShipPropsObject();
			String connectionFactoryName = shipPropsObj.getProperty("DEFAULT_CONNECTION_FACTORY");
			String destinationName = shipPropsObj.getProperty("DEFAULT_DESTINATION");
			String textMessage = shipPropsObj.getProperty("DEFAULT_MESSAGE");
			String userNameString = shipPropsObj.getProperty("DEFAULT_USERNAME");
			String passwordString = shipPropsObj.getProperty("DEFAULT_PASSWORD");
			String initialContextFactory = shipPropsObj.getProperty("INITIAL_CONTEXT_FACTORY");
			String providerUrl = shipPropsObj.getProperty("PROVIDER_URL");
			String userName = System.getProperty("username", userNameString);
	        String password = System.getProperty("password", passwordString);
	        
	        
	        try {
	            // Set up the context for the JNDI lookup
	            final Properties env = new Properties();
	            env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
	            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, providerUrl));
	            //env.put(Context.SECURITY_PRINCIPAL, userName);
	            //env.put(Context.SECURITY_CREDENTIALS, password);
	            context = new InitialContext(env);

	            // Perform the JNDI lookups
	            String connectionFactoryString = System.getProperty("connection.factory", connectionFactoryName);
	            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
	            
	            String destinationString = System.getProperty("destination", destinationName.trim());
	            System.out.println(" Queue Name : "+destinationString);
	            destination = (Destination) context.lookup(destinationString);
	            
	            int count = Integer.parseInt(messageCount);
	            
	            // Create the JMS connection, session, producer
	            // make sure to add the password created in Main WFS startup
	            System.out.println(userName +"    "+password);
	            connection = connectionFactory.createConnection(userName, password);
	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            producer = session.createProducer(destination);
	            
	            connection.start();
	            
	            log.info("Sending message");
	            message = session.createTextMessage(textMessage);
	            for (int i = 0; i < count; i++) {
		            producer.send(message);
	            }
	            log.info("details send to : "+destination);
	        } catch (NamingException | JMSException | NumberFormatException e) {
	            log.severe(e.getMessage());
	           e.printStackTrace();
	        } finally {
	        	try {
	        		if (context != null) {
		                context.close();
		            }

		            // closing the connection takes care of the session, producer, and consumer
		            if (connection != null) {
		                connection.close();
		            }
				} catch (Exception e2) {
					e2.printStackTrace();
				}
	        }
	}
	
	private Properties getShipPropsObject() {
		CartPropertyReader propertyReader = new CartPropertyReader();
		return propertyReader.getCartPropsObj();
	}
	
	public static void main(String[] args) {
		SendShippingDetails producerConsumer = new SendShippingDetails();
		producerConsumer.sendDetails("2");
	}
}
