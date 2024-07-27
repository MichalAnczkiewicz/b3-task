package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.TimeoutError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

public class BasePage {

    private static final Logger LOGGER = LogManager.getLogger(BasePage.class);
    protected final Page page;

    public BasePage(Page page) {

        this.page = page;
    }

    protected void click(Locator locator) {

        try {

            LOGGER.info("Clicking on element with locator: " + locator);
            locator.hover();
            locator.click();
        } catch (TimeoutError timeoutError) {

            throw new TimeoutError("Element not found for clicking: " + locator);
        }
    }

    protected boolean isElementVisible(Locator locator) {

        waitForElementToBeVisible(locator);
        return locator.isVisible();
    }

    protected void enterTextToInput(Locator locator, String value) {

        waitForElementToBeVisible(locator);
        locator.fill(value);
    }

    protected void hoverOverElement(Locator locator) {

        waitForElementToBeVisible(locator);
        page.evaluate("window.scrollTo(0,0)");
        locator.hover();
    }

    protected void clickAndWaitForNavigation(Locator locator, String urlToWaitFor) {

        click(locator);
        page.waitForURL(String.format("**/%s*", urlToWaitFor));
    }

    protected String getTextFromElement(Locator locator) {

        waitForElementToBeVisible(locator);
        return locator.textContent().trim();
    }

    protected void waitForElementToBeVisible(Locator locator) {

        try {

            locator.first().waitFor(new Locator.WaitForOptions().setState(VISIBLE));
        } catch (TimeoutError error) {
            throw new TimeoutError("Element not found: " + locator);
        }
    }
}
