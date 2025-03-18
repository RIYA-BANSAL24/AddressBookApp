package com.AddressBookApp.consumer;

import com.AddressBookApp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class MessageListener {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void receiveMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}