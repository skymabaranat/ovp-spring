Feature: testingCarEndpoints
  Background:
    Given the user wants to create a Car with the following details
      | brand | model | price | year | mileage | colour |
      | Tesla | A1 | 99511 | 2010 | 10000 | White |

  Scenario: the client makes a POST request to the cars/admin endpoint
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 201
    And the response body should contain the text '{"description":"Database updated"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with a JSON which has a missing attribute
    Given the user wants to create a Car with the following details
      | [blank] | model | price | year | mileage | colour |
      |   BMW   | A1 |  873  |  yes  | 10000 | White  |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with a JSON which has a missing field
    Given the user wants to create a Car with the following details
      | brand |  model  | price | year | mileage | colour |
      | Kia | [blank] | [blank] | 2010 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with an invalid year
    Given the user wants to create a Car with the following details
      | brand | model | price | year | mileage | colour |
      | Tesla | A1 | 11246 | 20818 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint using a duplicate Car
    Given the user wants to create a Car with the following details
      | brand | model | price | year | mileage | colour |
      | Tesla | A1 | 99511 | 2010 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 409
    And the response body should contain the text '{"description":"Car already exists"}'

  Scenario: the client makes a GET request to the cars/admin endpoint and a list of cars are returned
    When a GET request is made to the 'cars/admin' endpoint
    Then it should return a 200 response
    And the response body should contain the text 'CREATED'
