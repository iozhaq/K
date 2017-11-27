package com.kaishengit.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 基于注解的监听
 * @author fankay
 */
@Component
public class MyJmsListener {

    /*@JmsListener(destination = "message-queue",containerFactory = "jmsListenerContainerFactory")
    public void getMessageFromQueue(String message) {
        System.out.println("From queue message -> " + message);
    }*/

    @JmsListener(destination = "message-queue",containerFactory = "jmsListenerContainerFactory")
    public void getMessageFromQueue(Message message, Session session) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("From queue message -> " + textMessage.getText());
            if(1==1) {
                throw new JMSException("");
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "message-topic",containerFactory = "jmsTopicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        System.out.println("From topic message -> " + message);
    }


}
