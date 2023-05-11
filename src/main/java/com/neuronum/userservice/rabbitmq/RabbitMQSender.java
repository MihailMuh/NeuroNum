package com.neuronum.userservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    public void send(String routingKey, Object object) {
        rabbitTemplate.convertAndSend(routingKey, object);

        log.trace("Message: {} successfully sent", object);
    }
}
