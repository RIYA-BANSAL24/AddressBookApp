package com.AddressBookApp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "addressQueue";
    public static final String EXCHANGE_NAME = "addressExchange";
    public static final String ROUTING_KEY = "addressRoutingKey"; // FIXED HERE

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}