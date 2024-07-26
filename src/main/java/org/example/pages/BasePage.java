package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.TimeoutError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

public class BasePage {

    private static final Logger LOGGER
            = LogManager.getLogger(BasePage.class);
    private static final String REQUEST_CORRECTLY_FIRED = "Request correctly fired: ";
    protected final Page page;

    public BasePage(Page page) {

        this.page = page;
    }

    protected void click(Locator locator) {

        try {

            LOGGER.info("Klikam w element o lokatorze: " + locator);
            locator.hover();
            locator.click();
        } catch (TimeoutError timeoutError) {

            throw new TimeoutError("Nie znaleziono elementu do klikniecia: " + locator);
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
        page.waitForURL(String.format("**/%s*",urlToWaitFor));
//        page.waitForNavigation(() -> click(locator));
    }

    protected String getTextFromElement(Locator locator) {

        waitForElementToBeVisible(locator);
        return locator.textContent().trim();
    }

    protected void waitForElementToBeVisible(Locator locator) {

        try {

            locator.first().waitFor(new Locator.WaitForOptions().setState(VISIBLE));
        } catch (TimeoutError error) {
            throw new TimeoutError("Nie znaleziono elementu: " + locator);
        }
    }

    protected void clickAndWaitForRequestWithStatus(Locator locator, String request, int status) {

        Response resp = page.waitForResponse(
                response ->
                        response.status() == status && response.url().contains(request),
                () -> click(locator));
        LOGGER.info(REQUEST_CORRECTLY_FIRED + request);
    }
}
