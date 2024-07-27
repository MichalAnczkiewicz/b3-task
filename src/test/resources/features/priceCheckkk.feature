Feature: Checkout cart test

  @Regression
  Scenario: Test
    Given I am on the homepagex
    When I search for a productx
    Then I save the price of the productx
    When I add the product to the cartx
    Then the price of the product in the cart should match the saved pricex