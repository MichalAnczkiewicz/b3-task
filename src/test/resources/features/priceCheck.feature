Feature: Checkout cart

  @Regression
  Scenario: Check if searched product price is the same on details and checkout cart
    Given I am on the homepage "https://www.g2a.com"
    When I search for a product
    Then I save the price of the product
    When I add the product to the cart
    Then the price of the product in the cart should match the saved price