package com.neuronum.familia.rabbitmq;

import com.neuronum.selentity.SeleniumData;
import com.neuronum.familia.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queuesToDeclare = @Queue(
        value = "${spring.application.name}"
))
public class ClientConsumer {
    private final ClientService clientService;

    @RabbitHandler
    public void receiveClient(SeleniumData seleniumData) {
        log.trace("Message successfully received: {}", seleniumData);

        clientService.registerSelenium(seleniumData);
    }
}
