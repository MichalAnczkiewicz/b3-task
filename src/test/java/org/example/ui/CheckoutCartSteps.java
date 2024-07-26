package org.example.ui;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.cart.CheckoutCart;
import org.example.pages.product.ProductDetailsPage;
import org.example.pages.search.SearchPage;
import org.example.statics.ServiceUrls;
import org.example.utils.BrowserConfig;
import org.example.utils.ProductNameGetter;
import org.example.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;

public class CheckoutCartSteps {

    private final BrowserConfig browserConfig = new BrowserConfig();
    private final Page page = browserConfig.createPage();

    private TestHelpers testHelpers;
    private ProductDetailsPage productDetailsPage;
    private String priceOnProductCard;
    private static final String productName = ProductNameGetter.PRODUCT_NAME_PROPERTY;

    @Before
    public void setup() {

        testHelpers = new TestHelpers(page);
        productDetailsPage= new ProductDetailsPage(page);
    }

    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {

        testHelpers.openWebsite(ServiceUrls.HOMEPAGE);
    }
    @When("I search for a product")
    public void i_search_for_a_product() {

        SearchPage searchPage = new SearchPage(page);
        searchPage.clickSearchInput();
        searchPage.enterText(productName);
        searchPage.clickGameTitle(productName);
    }
    @Then("I save the price of the product")
    public void i_save_the_price_of_the_product() {

        priceOnProductCard = productDetailsPage.getProductPrice();
    }
    @When("I add the product to the cart")
    public void i_add_the_product_to_the_cart() {

        productDetailsPage.addToCartButton();
    }
    @Then("the price of the product in the cart should match the saved price")
    public void the_price_of_the_product_in_the_cart_should_match_the_saved_price() {

        String priceOnCart = new CheckoutCart(page).getProductPrice();
        Assertions.assertEquals(priceOnCart, priceOnProductCard);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        System.out.println("Jestem w after stepie");
        try {
            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot, "image/png", scenario.getName() + Math.random() * 1000000 );
            System.out.println(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {

        browserConfig.closeBrowser();
    }
}
