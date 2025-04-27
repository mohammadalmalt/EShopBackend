Feature: Product Retrieval

  Scenario: Retrieve all products without authentication
    When I retrieve all products without authentication
    Then the response should return status 200
    And the response should contain a list of products