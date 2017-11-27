package com.kaishengit.mq;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * pub/sub 监听器
 * @author fankay
 */
@Component
public class MyTopicListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("From Topic Message -> " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
