package com.neuronum.familia.service;

import com.neuronum.selentity.SeleniumData;
import com.neuronum.familia.repository.SeleniumRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final PageService pageService;

    private final ScheduledExecutorService scheduledExecutor;

    private final SeleniumRepository seleniumRepository;

    @Value("${spring.application.name}")
    private String accessKey;

    public void registerSelenium(SeleniumData seleniumData) {
        seleniumData.setId(accessKey);
        seleniumRepository.save(seleniumData);

        scheduleCollecting(seleniumData);
        scheduledExecutor.execute(() -> {
            seleniumData.setInterrupted(false);
            log.debug("Selenium doesn't more interrupted");
        });
    }

    private void scheduleCollecting(SeleniumData seleniumData) {
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            pageService.preparePages(seleniumData);
            pageService.forEachCall(call -> {
                log.info(call.toString());
            });
        }, 0, 60, TimeUnit.MINUTES);

        log.debug("Collecting has been scheduled");
    }

    @PostConstruct
    private void recoverySeleniumData() {
        seleniumRepository.findById(accessKey).ifPresent(this::scheduleCollecting);
    }
}
