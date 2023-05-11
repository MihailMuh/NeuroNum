package com.neuronum.userservice.controller;

import com.neuronum.userservice.entity.Client;
import com.neuronum.selentity.SeleniumData;
import com.neuronum.userservice.exception.ClientAlreadyRegisteredException;
import com.neuronum.userservice.exception.IncorrectAccessKeyException;
import com.neuronum.userservice.rabbitmq.RabbitMQSender;
import com.neuronum.userservice.repository.ClientRepository;
import com.neuronum.userservice.repository.KeyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
class ClientController {
    private final ClientRepository clientRepository;

    private final KeyRepository keyRepository;

    private final RabbitMQSender rabbitMQSender;

    @PostMapping("/form/no-telephony")
    public ResponseEntity<String> processClientFormWithoutSelenium(@RequestBody @Valid Client client) {
        checkClientRegistration(client);
        checkAccessKey(client.getAccessKey());
        clientRepository.save(client);

        log.trace("Client: {} successfully registered", client);
        return ResponseEntity.ok("Client successfully registered!");
    }

    @PostMapping("/form")
    public ResponseEntity<String> processClientForm(@RequestBody ClientForm clientForm) {
        Client client = clientForm.client();
        SeleniumData seleniumData = clientForm.seleniumData();

        checkClientRegistration(client);
        checkAccessKey(client.getAccessKey());

        clientRepository.save(client);

        sendSeleniumForm(client.getAccessKey(), seleniumData);

        log.trace("Client: {} successfully registered", client);
        return ResponseEntity.ok("Client successfully registered!");
    }

    private void sendSeleniumForm(String accessKey, SeleniumData seleniumData) {
        rabbitMQSender.send(accessKey, seleniumData);
    }

    private void checkAccessKey(String key) {
        if (!keyRepository.existsById(key)) {
            throw new IncorrectAccessKeyException();
        }
    }

    private void checkClientRegistration(Client client) {
        if (clientRepository.existsById(client.getEmail())) {
            throw new ClientAlreadyRegisteredException();
        }
    }

    private record ClientForm(@Valid Client client, @Valid SeleniumData seleniumData) {
    }
}
