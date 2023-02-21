package com.example.functional.definitions;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestSteps {

    private Response testResponse;
    private DataTable dataTable;
    private ValidatableResponse validatableResponse;
    RequestSpecification requestSpecification;

    ObjectMapper mapper = new ObjectMapper();

    @Given("the user wants to create a Car with the following details")
    public void the_user_wants_to_create_a_car_with_the_following_details(List<Map<String,String>> dataTable) throws JsonProcessingException {
        System.out.println("DATATABLE *******" + dataTable);
        String car = mapper.writeValueAsString(dataTable);
        System.out.println("CAR *******" + car);
        requestSpecification = given().contentType(ContentType.JSON).body(car);
    }

    @When("a GET request is made to the {string} endpoint")
    public void a_request_is_made_to_the_endpoint(String endpoint) {
        testResponse = given().get(endpoint);
    }

    @When("a POST request is made to the {string} endpoint")
    public void a_post_request_is_made_to_the_endpoint(String endpoint) {
        testResponse = requestSpecification.post(endpoint);
    }

    @Then("it should return a {int} response")
    public void it_should_return_a_response(Integer expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, testResponse.getStatusCode());
    }
    @Then("the client receives status code of {int}")
    public void the_client_should_return_a_response(Integer expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, testResponse.getStatusCode());
    }

    @And("the response body should contain the text {string}")
    public void the_response_body_should_contain_the_text(String expectedResponseBody) {
        Assertions.assertEquals(expectedResponseBody, testResponse.getBody().asString());
    }

    @And("the response body should contain the cars")
    public void the_response_body_should_contain_the_cars(List<Map<String, String>> expectedResponseBody) throws JsonProcessingException {
        String jsonString = testResponse.asString();
        String eB =  mapper.writeValueAsString(expectedResponseBody);
//        List<Map<String, String>> cars = JsonPath.from(jsonString).get("cars");
        Assertions.assertEquals(eB, testResponse.getBody().asString());
        // todo integers are returned as strings
    }
}
