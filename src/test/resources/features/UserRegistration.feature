Feature: User Registration

  Scenario: Attempt to register a user with an existing username
    Given a user with username "testuser" is already registered
    When I attempt to register a user with username "testuser", password "password", email "newuser@example.com", and role "CUSTOMER"
    Then the registration should fail with status 409