 package org.example.ui;

 import io.cucumber.java.en.Given;
 import io.cucumber.java.en.Then;
 import io.cucumber.java.en.When;
 import org.example.pages.cart.CheckoutCart;
 import org.example.pages.product.ProductDetailsPage;
 import org.example.pages.search.SearchPage;
 import org.example.statics.ServiceUrls;
 import org.example.utils.ProductNameGetter;
 import org.example.utils.TestHelpers;
 import org.junit.jupiter.api.Assertions;

 public class CheckoutCartStepsSecond {

     private static final String productName = ProductNameGetter.PRODUCT_NAME_PROPERTY;
     
     private final TestContext testContext;
     private final ProductDetailsPage productDetailsPage;
     private final TestHelpers testHelpers;
     
     private String priceOnProductCard;

     public CheckoutCartStepsSecond(TestContext testContext) {
         this.testContext = testContext;
         productDetailsPage = new ProductDetailsPage(testContext.getPage());
         testHelpers = new TestHelpers(testContext.getPage());
     }

     @Given("I am on the homepage second")
     public void i_am_on_the_homepage_second() {

         testHelpers.openWebsite(ServiceUrls.HOMEPAGE);
     }

     @When("I search for a product second")
     public void i_search_for_a_product_second() {

         SearchPage searchPage = new SearchPage(testContext.getPage());
         searchPage.clickSearchInput();
         searchPage.enterText(productName);
         searchPage.clickGameTitle(productName);
     }

     @Then("I save the price of the product second")
     public void i_save_the_price_of_the_product_second() {

         priceOnProductCard = productDetailsPage.getProductPrice();
     }

     @When("I add the product to the cart second")
     public void i_add_the_product_to_the_cart_second() {

         productDetailsPage.addToCartButton();
     }

     @Then("the price of the product in the cart should match the saved price second")
     public void the_price_of_the_product_in_the_cart_should_match_the_saved_price_second() {

         Assertions.assertEquals(new CheckoutCart(testContext.getPage()).getProductPrice(), priceOnProductCard);
     }
 }
