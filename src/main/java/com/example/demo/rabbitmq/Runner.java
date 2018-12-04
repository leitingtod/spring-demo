package com.example.demo.rabbitmq;

import com.example.demo.DemoApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----> Sending message...");
        rabbitTemplate.convertAndSend(DemoApplication.topicExchangeName, "foo.bar.baz", "ScheduledTask works!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}