package org.example.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class TestHelpers {

    private static final String URL = "https://g2a.com";
    private static final Logger LOGGER = LogManager.getLogger(TestHelpers.class);
    private final Page page;

    public TestHelpers(Page page) {

        this.page = page;
    }

    public void openWebsite(String url) {

        boolean success = false;
        int maxRetries = 3;

        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            try {
                page.waitForResponse(response -> response.url().contains(url) && response.status() == 200,
                        () -> page.navigate(URL));
                success = true;
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                break;
            } catch (TimeoutError e) {

                LOGGER.error(String.format("The website %s didn't respond for %d times", url, retryCount + 1));
            }
        }

        if (!success) {
            Assertions
                    .fail(String.format("Website %s didn't respond %d number of times. Test ended.", url, maxRetries));
        }
    }
}
