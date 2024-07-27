Feature: Checkout cart test

  @Regression
  Scenario: Test
    Given I am on the homepage second 
    When I search for a product second 
    Then I save the price of the product second 
    When I add the product to the cart second 
    Then the price of the product in the cart should match the saved price second 