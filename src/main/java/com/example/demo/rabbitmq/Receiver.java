package com.example.demo.rabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("----> Received <" + message + ">\n");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}