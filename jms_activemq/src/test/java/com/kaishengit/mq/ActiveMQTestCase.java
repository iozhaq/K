package com.kaishengit.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

public class ActiveMQTestCase {


    @Test
    public void sendMessageToQueue() throws JMSException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3.创建Session(两个参数：第一个参数表示是否使用事务，第二个参数指定签收消息的模式)
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        //4.创建 Destination 目的地对象
        Destination destination = session.createQueue("test-Message");
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(destination);
        //设置持久化模式
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //6.创建消息
        TextMessage textMessage = session.createTextMessage("Hello,MQ-6");
        //7.发送消息
        messageProducer.send(textMessage);

        //提交事务
        session.commit();

        //8.释放资源
        messageProducer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumerMessageFromQueue() throws JMSException, IOException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3.创建Session
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地
        Destination destination = session.createQueue("test-Message");
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6.消费消息,监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(">>> " + textMessage.getText());
                    //手动签收消息，队列删除
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
