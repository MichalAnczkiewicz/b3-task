Feature: Checkout cart

  @Regression2
  Scenario: Check if searched product price is the same on details and checkout cart
    Given I am on the homepage
    When I search for a product
    Then I save the price of the product
    When I add the product to the cart
    Then the price of the product in the cart should match the saved price