Feature: Full Order Cycle
Scenario: User registers, admin adds a product, and user orders the product
Given a user registers with username "customer", password "password", email "customer@example.com", and role "CUSTOMER"
And an admin is logged in with username "admin" and password "adminpassword"
When the admin adds a product with name "Laptop", description "High-performance laptop", price 999.99, and stock 10
Then the product should be added successfully
When the user logs in with username "customer" and password "password"
And the user orders the product with name "Laptop" and quantity 2
Then the order should be created successfully
And the order details should include product "Laptop" and quantity 2