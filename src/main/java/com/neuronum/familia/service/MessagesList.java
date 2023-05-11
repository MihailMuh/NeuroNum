package com.neuronum.familia.service;

import com.neuronum.familia.exception.AllMessagesAnalysedException;
import com.neuronum.familia.selenium.browser.Browser;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Accessors(fluent = true)
@Scope(value = SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class MessagesList implements Iterable<String>, Iterator<String> {
    private final Browser browser;

    private final CallService callService;

    private List<WebElement> elementsArchitecture = Collections.emptyList();

    private int currentMessageNumber, currentPageNumber;

    @Setter
    private boolean wasInterrupted;

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (currentMessageNumber < elementsArchitecture.size()) {
            return true;
        }

        // if reached the top of the page
        try {
            nextPage();
            return true;
        } catch (ElementNotInteractableException ignored) { // if button "Дальше" not presents
            log.trace("There are no available pages!");
            return false;
        }
    }

    @Override
    public String next() {
        String message = getNextMessage();

        if (callService.existsById(message)) {
            if (!wasInterrupted) throw new AllMessagesAnalysedException();

            wasInterrupted = false;
            return jumpToInterruptedMessage();
        }

        return message;
    }

    @SneakyThrows
    private String getNextMessage() {
        triggerMessenger();
        Thread.sleep(500);

        String message = collectMessageToString();

        currentMessageNumber++;
        return message;
    }

    private String jumpToInterruptedMessage() {
        while (hasNext()) {
            log.trace("--------------- JUMPING ---------------");

            String message = getNextMessage();
            if (!callService.existsById(message)) {
                return message;
            }
        }

        log.trace("No any messages(");
        return "";
    }

    private void nextPage() {
        browser.find(xpath("//span[text()='дальше']")).click();

        // wait for loading page
        browser.Wait().until(presenceOfElementLocated(cssSelector("div[class='x-panel-body x-grid-with-row-lines x-grid-body x-panel-body-ul x-panel-body-ul x-summary-top']")));
        updateElementsArchitecture();

        currentMessageNumber = 0;
        currentPageNumber++;

        log.trace("Go to next page. Now current number of page: {}", currentPageNumber);
    }

    private String collectMessageToString() {
        String messagesString = String.valueOf(browser.javaScript("""
                let messagesString = "";

                for (const message of document.getElementsByClassName("speechanalytics-call-transcription-field-text")) {
                    const messageDivColor = window.getComputedStyle(message, null).getPropertyValue('background-color');
                    if (messageDivColor === "rgb(255, 255, 255)") {
                        messagesString += "Клиент: " + message.innerText + "\\n";
                    } else {
                        messagesString += "Администратор: " + message.innerText + "\\n";
                    }
                }

                return messagesString;
                """));

        log.trace("Messages collected");
        return messagesString;
    }

    private void updateElementsArchitecture() {
        elementsArchitecture = (List<WebElement>) browser.javaScript("""
                const tagElements = [];
                                
                for (const table of document.getElementsByClassName("x-grid-item-container")[1].children) {
                    tagElements.push(table.firstElementChild.firstElementChild.children[2].firstElementChild)
                }

                return tagElements;
                """);

        log.trace("Architecture updated");
    }

    private void triggerMessenger() {
        browser.javaScript("arguments[0].click()", elementsArchitecture.get(currentMessageNumber));

        log.trace("Current message number: {}. Messenger triggered", currentMessageNumber + 1);
    }
}
