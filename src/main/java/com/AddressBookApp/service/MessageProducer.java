package com.AddressBookApp.service;

import com.AddressBookApp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        try {
            System.out.println("Sending message to RabbitMQ: " + message);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY, // FIXED HERE
                    message
            );
            System.out.println("Message sent successfully to RabbitMQ.");
        } catch (Exception e) {
            System.err.println("Error while sending message to RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}