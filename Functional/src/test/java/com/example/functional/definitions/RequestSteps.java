package com.example.functional.definitions;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestSteps {

    private Response testResponse;
    private DataTable dataTable;
    private ValidatableResponse validatableResponse;
    RequestSpecification requestSpecification;

    ObjectMapper mapper = new ObjectMapper();

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String listOfStringListsType(String cell) {
        return cell;
    }

    @DataTableType
    public CarDTO carEntryTransformer(Map<String, String> row) {
        return new CarDTO(
                row.get("brand"),
                row.get("model"),
                Integer.parseInt(row.get("year")),
                Integer.parseInt(row.get("price")),
                Integer.parseInt(row.get("mileage")),
                row.get("colour")
        );
    }

    @Given("the user wants to create a Car with the following details")
    public void the_user_wants_to_create_a_car_with_the_following_details(List<Map<String,String>> dataTable) throws JsonProcessingException {
        System.out.println("********DATATABLE*******" + dataTable);
        String car = mapper.writeValueAsString(dataTable);
        System.out.println("********CAR********" + car);
        requestSpecification = given().contentType(ContentType.JSON).body(car);

    }
    @Given("there exists a Car in the database with the following details")
    public void there_exists_a_car_with_the_following_details(List<CarDTO> dataTable) throws JsonProcessingException {
        requestSpecification = given().contentType(ContentType.JSON).body(dataTable);
        System.out.println("********requestSpecification*******" + requestSpecification);
        requestSpecification.post("/cars/admin");
    }

    @When("a GET request is made to the {string} endpoint")
    public void a_get_request_is_made_to_the_endpoint(String endpoint) {
        testResponse = given().get(endpoint);
    }

    @When("a POST request is made to the {string} endpoint")
    public void a_post_request_is_made_to_the_endpoint(String endpoint) {
        testResponse = requestSpecification.post(endpoint);
    }
    @When("a PUT request is made to the {string} endpoint with the request body")
    public void a_put_request_is_made_to_the_endpoint(String endpoint, List<Map<String,String>> dataTable) throws JsonProcessingException {
        String carBody =  mapper.writeValueAsString(dataTable);
        testResponse = requestSpecification.put(endpoint, carBody);
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
    public void the_response_body_should_contain_the_cars(List<CarDTO> expectedResponseBodyDataTable) throws JsonProcessingException {
        System.out.println("********expectedResponseBody********" + expectedResponseBodyDataTable);

        String expectedResponseBody =  mapper.writeValueAsString(expectedResponseBodyDataTable);
        System.out.println("********expectedBody********" + expectedResponseBody);
//        testResponse = given().contentType(ContentType.JSON).body(expectedResponseBody);

        Assertions.assertEquals(expectedResponseBody, testResponse.getBody().asString());
    }
}