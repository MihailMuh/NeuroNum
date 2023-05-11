package com.neuronum.familia.selenium.browser;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrowserImpl implements Browser {
    private final WebDriver driver;

    @Override
    public File screenshot() {
        log.trace("Screenshot has been taken");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    @Override
    @PreDestroy
    public void shutdown() {
        log.trace("Disposing the browser...");
        driver.quit();
        log.trace("Success!");
    }

    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public Object javaScript(String script, Object... args) {
        log.trace("Executing of JavaScript...");
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @Override
    public WebElement find(By by) {
        return driver.findElement(by);
    }

    @Override
    public WebDriverWait Wait(int seconds) {
        log.trace("Explicit wait has been created for {} seconds", seconds);
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
}
