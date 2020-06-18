package com.webstore.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
  
@Component
public class MessageSender {
 
    @Autowired
    JmsTemplate jmsTemplate;
 
    public void sendMessage(final String order) {
 
        jmsTemplate.send(new MessageCreator(){
                @Override
                public Message createMessage(Session session) throws JMSException{
                	System.out.println("MessageSender.sendMessage(...).new MessageCreator() {...}.createMessage()" + order );
                    TextMessage textMessage = session.createTextMessage(order);
                    return textMessage;
                }
            });
    }
 
}

