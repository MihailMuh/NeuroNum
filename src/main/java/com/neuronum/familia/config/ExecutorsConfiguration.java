package com.neuronum.familia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
class ExecutorsConfiguration {
    @Bean
    public ScheduledExecutorService scheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
