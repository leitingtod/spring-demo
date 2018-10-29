package com.example.demo.rabbitmq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public ScheduledTasks(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        try {
            String message = "The time is now " + dateFormat.format(new Date());
            log.info(message);
            System.out.println("----> Sending scheduled message...");
            rabbitTemplate.convertAndSend(DemoApplication.topicExchangeName, "foo.bar.baz", message);
            receiver.getLatch().await(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}