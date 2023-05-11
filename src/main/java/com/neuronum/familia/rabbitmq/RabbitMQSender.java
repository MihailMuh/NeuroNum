package com.neuronum.familia.rabbitmq;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    public void send(String routingKey, Object object) {
        rabbitTemplate.convertAndSend(routingKey, object);

        log.trace("Message: {} successfully sent to queue: {}", object, routingKey);
    }

    @Nullable
    public <T> T get(String queueName, Class<T> clazz) {
        log.trace("Receiving object: {} from queue: {}", clazz.toString(), queueName);

        return rabbitTemplate.receiveAndConvert(queueName, ParameterizedTypeReference.forType(clazz));
    }
}
