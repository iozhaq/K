package com.kaishengit.mq;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class QuereConsuerm implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("@@@@@@@@@ " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
