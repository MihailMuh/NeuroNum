package com.neuronum.familia.selenium.pages;

import com.neuronum.familia.selenium.browser.Browser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class LoginPage extends Page {
    @CacheLookup
    @FindBy(css = "input[class='b-auth__emailinput b-auth__emailinput--js_init']")
    private WebElement emailInput;

    @CacheLookup
    @FindBy(css = "input[class='b-auth__passwordinput b-auth__passwordinput--js_init']")
    private WebElement passwordInput;

    @CacheLookup
    @FindBy(css = "div[class='b-auth__button b-auth__button--js_init']")
    private WebElement loginButton;

    public LoginPage(Browser browser, @Value("${spring.browser.url.login}") String loginUrl) {
        super(browser, loginUrl);

        browser.get(url);
        PageFactory.initElements(browser.getDriver(), this);

        log.trace("LoginPage has been initialized");
    }

    public void inputEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);

        log.trace("Email {} has been entered", email);
    }

    public void inputPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);

        log.trace("Password {} has been entered", password);
    }

    public void login() {
        loginButton.click();

        // wait for logining
        browser.Wait().until(visibilityOfAllElementsLocatedBy(xpath("//span[text()='Поддержка']")));

        log.trace("Login completed successfully");
    }
}
