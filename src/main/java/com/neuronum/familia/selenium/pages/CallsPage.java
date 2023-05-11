package com.neuronum.familia.selenium.pages;

import com.neuronum.familia.entity.Call;
import com.neuronum.familia.selenium.browser.Browser;
import com.neuronum.familia.service.MessagesList;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Accessors(fluent = true)
@Scope(value = SCOPE_PROTOTYPE)
public class CallsPage extends Page {
    @Setter
    private boolean wasInterrupted;

    public CallsPage(Browser browser, @Value("${spring.browser.url.calls}") String url) {
        super(browser, url);

        browser.get(formatUrl(LocalDateTime.of(2023, 3, 1, 0, 0), LocalDateTime.now()));

        // while this button non-clickable, messages not displayed on page
        browser.Wait().until(elementToBeClickable(xpath("//span[text()='дальше']")));

        log.debug("MessagesPage has been initialized");
    }

    public void forEachCall(Consumer<Call> callConsumer) {
        MessagesList messagesList = getMessagesList();
        messagesList.wasInterrupted(wasInterrupted);

        log.trace("Start message iterating");

        for (String message : messagesList) {
            callConsumer.accept(generateCall(message, collectPhoneNumber(), collectCallDateTime()));
        }

        log.trace("Message iterating has been finished");
    }

    private String collectPhoneNumber() {
        String phoneNumber = browser.find(cssSelector("div[class='x-form-display-field x-form-display-field-cm-l-displayfield']")).getText();

        log.info("Phone number collected");
        return phoneNumber;
    }

    private String collectCallDateTime() {
        String callDateTime = browser.Wait().until(visibilityOfElementLocated(cssSelector("div[class='x-form-display-field x-form-display-field-cm-s-gray-displayfield']"))).getText();

        log.info("Call date time collected");
        return callDateTime;
    }

    private Call generateCall(String callRecord, String phoneNumber,
                              String date) {  // Example of date "12.03 в 11:47"
        Call call = new Call();
        call.setDate(date.split(" в ")[0]);
        call.setTime(date.split(" в ")[1]);
        call.setPhoneNumber(phoneNumber);
        call.setText(callRecord);

        return call;
    }

    private String formatUrl(LocalDateTime oldestDate, LocalDateTime newestDate) {
        String oldest = oldestDate.format(DateTimeFormatter.ISO_DATE);
        String newest = newestDate.format(DateTimeFormatter.ISO_DATE);

        log.info("Current date range: {} --- {}", oldest, newest);
        return url.replace("endDate=%222023-03-01", "endDate=%22" + newest)
                .replace("startDate=%222021-03-01", "startDate=%22" + oldest);
    }

    @Lookup
    public MessagesList getMessagesList() {
        return null;
    }
}
