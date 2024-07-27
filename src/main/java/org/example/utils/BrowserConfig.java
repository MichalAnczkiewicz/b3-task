package org.example.utils;

import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.example.utils.BrowsersTypes.*;

public class BrowserConfig {

    private static final Logger LOGGER
            = LogManager.getLogger(BrowserConfig.class);
    private static final String SELECTED_BROWSER_MESSAGE = "Selected: %s, version %s";
    private static final String BROWSER_PROPERTY = System.getProperty("BROWSER", "CHROME");
    
    private Playwright playwright;
    private BrowserContext browserContext;

    public Page createPage() {

        browserContext = createBrowserContext();
        return browserContext.newPage();
    }

    public void closeBrowser() {

        playwright.close();
    }

    private BrowserContext createBrowserContext() {

        playwright = Playwright.create();

        Browser browser = determineBrowser();
        Browser.NewContextOptions newContextOptions = createNewContextOptions();

        browserContext = browser.newContext(newContextOptions);
        browserContext.addInitScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        return browserContext;
    }

    private Browser.NewContextOptions createNewContextOptions() {

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();

        newContextOptions
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(1920, 1080)
                .setLocale("pl-PL")
                .setTimezoneId("Europe/Warsaw");

        return newContextOptions;
    }

    private Browser determineBrowser() {

        Browser browser;

        switch (BROWSER_PROPERTY.toLowerCase()) {
            case "firefox" -> {

                browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                LOGGER.info(String.format(SELECTED_BROWSER_MESSAGE, FIREFOX, browser.version()));
                return browser;
            }
            case "webkit" -> {

                browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                LOGGER.info(String.format(SELECTED_BROWSER_MESSAGE, WEBKIT, browser.version()));
                return browser;
            }

            default -> {

                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                LOGGER.info(String.format(SELECTED_BROWSER_MESSAGE, CHROME, browser.version()));
                return browser;
            }
        }
    }
}

