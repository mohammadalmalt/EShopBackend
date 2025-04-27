package com.berativ.eshopbackend.steps;

import com.berativ.eshopbackend.dto.ProductDTO;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductRetrievalSteps {

    @LocalServerPort
    private int port;

    private Response response;

    @When("I retrieve all products without authentication")
    public void i_retrieve_all_products_without_authentication() {
        response = RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/api/products");
    }

    @Then("the response should return status {int}")
    public void the_response_should_return_status(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain a list of products")
    public void the_response_should_contain_a_list_of_products() {
        List<ProductDTO> products = response.as(new TypeRef<List<ProductDTO>>() {});
        assertNotNull(products, "Product list should not be null");
    }
}