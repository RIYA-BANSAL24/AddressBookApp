package com.AddressBookApp.producer;

import com.AddressBookApp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSender{

    @Value("${rabbitmq.exchange.name}")
    private String EXCHANGE;
    @Value("${rabbitmq.routing.name}")
    private String ROUTING_KEY;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY, message);
        System.out.println("Sent Message: " + message);
    }
}