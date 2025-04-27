package com.berativ.eshopbackend.steps;

import com.berativ.eshopbackend.dto.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

public class OrderCycleSteps {

    @LocalServerPort
    private int port;

    private Response response;
    private String customerToken;
    private String adminToken;
    private ProductDTO createdProduct;
    private OrderDTO createdOrder;

    @Given("a user registers with username {string}, password {string}, email {string}, and role {string}")
    public void a_user_registers(String username, String password, String email, String role) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);
        userDTO.setRole(com.berativ.eshopbackend.model.User.Role.valueOf(role));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("http://localhost:" + port + "/api/auth/register")
                .then()
                .statusCode(200);
    }

    @Given("an admin is logged in with username {string} and password {string}")
    public void an_admin_is_logged_in(String username, String password) {
        UserDTO adminDTO = new UserDTO();
        adminDTO.setUsername(username);
        adminDTO.setPassword(password);
        adminDTO.setEmail("admin@example.com");
        adminDTO.setRole(com.berativ.eshopbackend.model.User.Role.ADMIN);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("http://localhost:" + port + "/api/auth/register")
                .then()
                .statusCode(200);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(username, password))
                .when()
                .post("http://localhost:" + port + "/api/auth/login");
        adminToken = response.as(AuthResponse.class).getToken();
    }

    @When("the admin adds a product with name {string}, description {string}, price {double}, and stock {int}")
    public void the_admin_adds_a_product(String name, String description, double price, int stock) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stock);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(productDTO)
                .when()
                .post("http://localhost:" + port + "/api/products");
    }

    @Then("the product should be added successfully")
    public void the_product_should_be_added_successfully() {
        response.then().statusCode(200);
        createdProduct = response.as(ProductDTO.class);
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in(String username, String password) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(username, password))
                .when()
                .post("http://localhost:" + port + "/api/auth/login");
        customerToken = response.as(AuthResponse.class).getToken();
    }

    @When("the user orders the product with name {string} and quantity {int}")
    public void the_user_orders_the_product(String productName, int quantity) {
        OrderDTO orderDTO = new OrderDTO();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(createdProduct.getId());
        orderItemDTO.setQuantity(quantity);
        orderDTO.setOrderItems(List.of(orderItemDTO));

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + customerToken)
                .body(orderDTO)
                .when()
                .post("http://localhost:" + port + "/api/orders");
    }

    @Then("the order should be created successfully")
    public void the_order_should_be_created_successfully() {
        response.then().statusCode(200);
        createdOrder = response.as(OrderDTO.class);
    }

    @Then("the order details should include product {string} and quantity {int}")
    public void the_order_details_should_include_product_and_quantity(String productName, int quantity) {
        Assertions.assertEquals(createdProduct.getId(), createdOrder.getOrderItems().get(0).getProductId());
        Assertions.assertEquals(quantity, createdOrder.getOrderItems().get(0).getQuantity());
    }
}