package com.example.functional.definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class RequestSteps {

    private Response testResponse;

    @When("a GET request is made to the {string} endpoint")
    public void a_get_request_is_made_to_the_endpoint(String endpoint) {
        testResponse = given().get(endpoint);
    }

    @Then("it should return a {int} response")
    public void it_should_return_a_response(Integer expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, testResponse.getStatusCode());
    }

    @Then("the response body should contain the text {string}")
    public void the_response_body_should_contain_the_text(String expectedResponseBody) {
        Assertions.assertEquals(expectedResponseBody, testResponse.getBody().asString());
    }


}
