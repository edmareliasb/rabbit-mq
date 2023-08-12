package com.example.spring.producer.amqp.implementation;

import com.example.spring.producer.amqp.AmqpProducer;
import com.example.spring.producer.dto.MessageQueue;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerRabbitMQ implements AmqpProducer<MessageQueue> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.exchange.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Override
    public void producer(MessageQueue message) {

        try {
            rabbitTemplate.convertAndSend(exchange, queue, message);
        } catch (Exception e) {
            //Envia diretamente para a DeadLetter Queue
            throw new AmqpRejectAndDontRequeueException(e);
        }


    }
}
