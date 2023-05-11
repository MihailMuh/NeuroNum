package com.neuronum.familia.selenium.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public interface Browser {
    File screenshot();

    void shutdown();

    void get(String url);

    WebDriver getDriver();

    Object javaScript(String script, Object... args);

    WebElement find(By by);

    default WebDriverWait Wait() {
        return Wait(60 * 3);
    }

    WebDriverWait Wait(int seconds);
}
