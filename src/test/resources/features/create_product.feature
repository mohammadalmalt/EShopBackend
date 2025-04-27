Feature: Product Creation
  As an admin
  I want to create a new product
  So that it can be available for purchase

  Scenario: Create a new product as an admin
    Given I am logged in as an admin
    When I create a product with name "Laptop", description "High-performance laptop", price 999.99, and stock 10
    Then the product should be created successfully
    And the product details should be correct

  Scenario: Attempt to create a product without authentication
    When I attempt to create a product with name "Laptop", description "High-performance laptop", price 999.99, and stock 10 without authentication
    Then the request should be denied with status 403