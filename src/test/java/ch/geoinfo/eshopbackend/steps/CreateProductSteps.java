package ch.geoinfo.eshopbackend.steps;

import ch.geoinfo.eshopbackend.dto.AuthRequest;
import ch.geoinfo.eshopbackend.dto.AuthResponse;
import ch.geoinfo.eshopbackend.dto.ProductDTO;
import ch.geoinfo.eshopbackend.dto.UserDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;

public class CreateProductSteps {

    @LocalServerPort
    private int port;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String adminToken;
    private Response response;
    private ProductDTO createdProduct;

    @Given("I am logged in as an admin")
    public void i_am_logged_in_as_an_admin() {
        // Register an admin user (if not already registered)
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setPassword("adminpassword");
        userDTO.setEmail("admin@example.com");
        userDTO.setRole(ch.geoinfo.eshopbackend.model.User.Role.ADMIN);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("http://localhost:" + port + "/api/auth/register")
                .then()
                .statusCode(200); // Ignore errors for existing user

        // Login to get JWT token
        Response loginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest("admin", "adminpassword"))
                .when()
                .post("http://localhost:" + port + "/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        adminToken = loginResponse.as(AuthResponse.class).getToken();
    }

    @When("I create a product with name {string}, description {string}, price {double}, and stock {int}")
    public void i_create_a_product_with_name_description_price_and_stock(String name, String description, double price, int stock) {
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

    @Then("the product should be created successfully")
    public void the_product_should_be_created_successfully() {
        response.then().statusCode(200);
        createdProduct = response.as(ProductDTO.class);
    }

    @Then("the product details should be correct")
    public void the_product_details_should_be_correct() {
        Assertions.assertEquals("Laptop", createdProduct.getName());
        Assertions.assertEquals("High-performance laptop", createdProduct.getDescription());
        Assertions.assertEquals(999.99, createdProduct.getPrice());
        Assertions.assertEquals(10, createdProduct.getStockQuantity());
    }
}