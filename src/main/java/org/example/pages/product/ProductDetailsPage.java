package org.example.pages.product;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.pages.BasePage;

import java.util.Locale;

public class ProductDetailsPage extends BasePage {

    private final Locator paymentInfo;
    private final Locator price;
    private final Locator addToCartButton;
    private final Locator productName;
    private final Locator modal;
    private final Locator modalAddToCartButton;

    public ProductDetailsPage(Page page) {

        super(page);
        this.paymentInfo = page.locator("div[data-locator='ppa-payment-info']");
        this.price = this.paymentInfo.locator("label[data-locator='ppa-payment'] span[data-locator='zth-price']");
        this.addToCartButton = this.paymentInfo.locator("button[data-locator='ppa-payment__btn']");
        this.productName = page.locator("h1[data-locator='ppa-summary__title']");
        this.modal = page.locator("div[data-test-id='dialog-container']");
        this.modalAddToCartButton = this.modal.locator("button[data-test-id='primary-button']");
    }

    public String getProductPrice() {

        return getTextFromElement(price);
    }

    public void addToCartButton() {
        if (getProductName().contains("account")) {
            click(addToCartButton);
        }
        clickAndWaitForNavigation(modalAddToCartButton, "**/page/cart*");
    }

    private String getProductName() {

        return getTextFromElement(productName).toLowerCase(Locale.ROOT);
    }
}
