package org.example.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutCartSteps {

    @Given("I am on the homepage {string}")
    public void i_am_on_the_homepage(String string) {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        System.out.println("test");
    }
    @When("I search for a product")
    public void i_search_for_a_product() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        System.out.println("test1");

    }
    @Then("I save the price of the product")
    public void i_save_the_price_of_the_product() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        System.out.println("test2");

    }
    @When("I add the product to the cart")
    public void i_add_the_product_to_the_cart() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        System.out.println("test3");

    }
    @Then("the price of the product in the cart should match the saved price")
    public void the_price_of_the_product_in_the_cart_should_match_the_saved_price() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        System.out.println("test4");
    }
}
