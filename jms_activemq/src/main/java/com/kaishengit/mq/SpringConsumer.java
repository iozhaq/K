package com.kaishengit.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SpringConsumer {

    @JmsListener(destination = "spring-Topic")
    public void doSomething(String message) {
        System.out.println("****** " + message);
    }

}
