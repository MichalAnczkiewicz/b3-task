package org.example.pages.search;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.pages.BasePage;

import java.util.Locale;

public class SearchPage extends BasePage {

    //could be local variable, but if test will be extended in the future, it may be helpful to leave it here as it may be reused
    private final String gameTitle = "li[data-url*='%s']";
    private final Locator searchInput;

    public SearchPage(Page page) {

        super(page);
        this.searchInput = page.locator("input[type='search']");
    }

    public void clickSearchInput() {

        click(searchInput);
    }

    public void enterText(String gameTitle) {

        enterTextToInput(searchInput, gameTitle);
    }

    public void clickGameTitle(String gameTitle) {

        gameTitle = gameTitle.replaceAll(" ", "-").toLowerCase(Locale.ROOT);
        //probably doesn't matter which one will be clicked, so the test will click the first found
        clickAndWaitForNavigation(page.locator(String.format(this.gameTitle, gameTitle)).first(), gameTitle);
    }
}
