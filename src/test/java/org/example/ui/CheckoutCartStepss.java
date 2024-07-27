// package org.example.ui;

// import io.cucumber.java.AfterStep;
// import io.cucumber.java.Scenario;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import org.example.pages.cart.CheckoutCart;
// import org.example.pages.product.ProductDetailsPage;
// import org.example.pages.search.SearchPage;
// import org.example.statics.ServiceUrls;
// import org.example.utils.ProductNameGetter;
// import org.example.utils.TestHelpers;
// import org.junit.jupiter.api.Assertions;

// public class CheckoutCartStepss {

//     private static final String productName = ProductNameGetter.PRODUCT_NAME_PROPERTY;

//     private TestContext testContext;
//     private String priceOnProductCard;
//     private ProductDetailsPage productDetailsPage = new ProductDetailsPage(testContext.getPage());
//     private TestHelpers testHelpers = new TestHelpers(testContext.getPage());

//     public CheckoutCartStepss(TestContext testContext) {
//         this.testContext = testContext;
//     }

//     @Given("I am on the homepagex")
//     public void i_am_on_the_homepagex() {

//         testHelpers.openWebsite(ServiceUrls.HOMEPAGE);
//     }

//     @When("I search for a productx")
//     public void i_search_for_a_productx() {

//         SearchPage searchPage = new SearchPage(testContext.getPage());
//         searchPage.clickSearchInput();
//         searchPage.enterText(productName);
//         searchPage.clickGameTitle(productName);
//     }

//     @Then("I save the price of the productx")
//     public void i_save_the_price_of_the_productx() {

//         priceOnProductCard = productDetailsPage.getProductPrice();
//     }

//     @When("I add the product to the cartx")
//     public void i_add_the_product_to_the_cartx() {

//         productDetailsPage.addToCartButton();
//     }

//     @Then("the price of the product in the cart should match the saved pricex")
//     public void the_price_of_the_product_in_the_cart_should_match_the_saved_pricex() {

//         Assertions.assertEquals(new CheckoutCart(testContext.getPage()).getProductPrice(), priceOnProductCard);
//     }

//     @AfterStep
//     public void afterStep(Scenario scenario) {

//         try {
//             byte[] screenshot = testContext.getPage().screenshot();
//             scenario.attach(screenshot, "image/png", scenario.getName());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
