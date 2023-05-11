package com.neuronum.familia.selenium.pages;

import com.neuronum.familia.selenium.browser.Browser;

public abstract class Page {
    protected final Browser browser;

    protected final String url;

    public Page(Browser browser, String url) {
        this.browser = browser;
        this.url = url;
    }
}
