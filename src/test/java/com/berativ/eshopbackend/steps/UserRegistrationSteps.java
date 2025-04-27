package com.berativ.eshopbackend.steps;

import com.berativ.eshopbackend.dto.UserDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;

public class UserRegistrationSteps {

    @LocalServerPort
    private int port;

    private Response response;

    @Given("a user with username {string} is already registered")
    public void a_user_with_username_is_already_registered(String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword("password");
        userDTO.setEmail("existinguser@example.com");
        userDTO.setRole(com.berativ.eshopbackend.model.User.Role.CUSTOMER);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("http://localhost:" + port + "/api/auth/register"); // No status check
    }

    @When("I attempt to register a user with username {string}, password {string}, email {string}, and role {string}")
    public void i_attempt_to_register_a_user(String username, String password, String email, String role) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);
        userDTO.setRole(com.berativ.eshopbackend.model.User.Role.valueOf(role));

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when()
                .post("http://localhost:" + port + "/api/auth/register");
    }

    @Then("the registration should fail with status {int}")
    public void the_registration_should_fail_with_status(int statusCode) {
        response.then().statusCode(statusCode);
    }
}