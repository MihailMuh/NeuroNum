package com.neuronum.familia.service;

import com.neuronum.familia.entity.Call;
import com.neuronum.selentity.SeleniumData;
import com.neuronum.familia.selenium.pages.CallsPage;
import com.neuronum.familia.selenium.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class PageService {
    private CallsPage callsPage;

    public void preparePages(SeleniumData seleniumData) {
        loginClient(seleniumData.getLogin(), seleniumData.getPassword());

        callsPage = getCallsPage();
        callsPage.wasInterrupted(seleniumData.isInterrupted());
    }

    public void forEachCall(Consumer<Call> callConsumer) {
        callsPage.forEachCall(callConsumer);
    }

    private void loginClient(String email, String password) {
        LoginPage loginPage = getLoginPage();
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.login();

        log.trace("Entered to client account");
    }

    @Lookup
    public LoginPage getLoginPage() {
        return null;
    }

    @Lookup
    public CallsPage getCallsPage() {
        return null;
    }
}
