package org.example.ui;

import org.example.utils.BrowserConfig;

import com.microsoft.playwright.Page;

public class TestContext {

    private BrowserConfig browserConfig;
    private Page page;

    public void setUp() {
        
        if (browserConfig == null) {
            browserConfig = new BrowserConfig();
            page = browserConfig.createPage();
        }
    }

    public void tearDown() {
        if (browserConfig != null) {
            browserConfig.closeBrowser();
        }
    }

    public Page getPage() {
        return page;
    }
}
