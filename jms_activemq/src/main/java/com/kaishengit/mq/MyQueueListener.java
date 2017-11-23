package com.kaishengit.mq;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * MQ中的Queue监听器
 * @author fankay
 */
@Component
public class MyQueueListener implements SessionAwareMessageListener {

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("From Queue Message -> " + textMessage.getText());
            if (1==1) {
                throw new JMSException("故意的异常");
            }
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }

    /*@Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("From Queue Message -> " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }*/
}
