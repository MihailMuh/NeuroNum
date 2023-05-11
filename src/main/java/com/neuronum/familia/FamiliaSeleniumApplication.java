package com.neuronum.familia;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableRabbit
@SpringBootApplication
@EntityScan(basePackages = "com.neuronum")
public class FamiliaSeleniumApplication {
    public static void main(String[] args) {
        SpringApplication.run(FamiliaSeleniumApplication.class, args);
    }
}
