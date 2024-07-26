package org.example.pages.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.pages.BasePage;

public class CheckoutCart extends BasePage {

    private final Locator productPrice;

    public CheckoutCart(Page page) {

        super(page);
        this.productPrice = page.locator("span[data-locator='zth-price']").first();
    }

    public String getProductPrice() {

        return getTextFromElement(productPrice);
    }
}
