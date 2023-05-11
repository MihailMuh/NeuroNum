package com.neuronum.familia.config;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SeleniumConfiguration {
    @Bean(destroyMethod = "")
    @SneakyThrows
    public WebDriver createBrowser(@Value("${spring.browser.url}") String driverUrl) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--no-sandbox",
                "--remote-allow-origins=*"
        );
//        options.setCapability("browserVersion", "112.0");
//        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
//            put("enableVNC", true);
//        }});

//        return new RemoteWebDriver(new URL(driverUrl), options, false);
        return new ChromeDriver(options);
    }
}
