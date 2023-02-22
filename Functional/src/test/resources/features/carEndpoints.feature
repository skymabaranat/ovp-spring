Feature: testingCarEndpoints
  Background:
    Given the user wants to create a Car with the following details
      | brand | model | year |  price | mileage | colour |
      | Tesla |   A1  | 2001 | 195000 | 10000 | White |

  Scenario: the client makes a POST request to the cars/admin endpoint
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 201
    And the response body should contain the text '{"description":"Database updated"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with a JSON which has a missing attribute
    Given the user wants to create a Car with the following details
      | brand | model | year | price | mileage | colour |
      |   BMW   | A1 |  2015  |  yes  | 10000 | White  |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with a JSON which has a missing field
    Given the user wants to create a Car with the following details
      | brand | model | year | price | mileage | colour |
      | Kia | [blank] | [blank] | 11300 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint with an invalid year
    Given the user wants to create a Car with the following details
      | brand | model | year | price | mileage | colour |
      | Tesla | A1 | 11246 | 20818 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 400
    And the response body should contain the text '{"description":"Incorrect car data provided"}'

  Scenario: the client makes a POST request to the cars/admin endpoint using a duplicate Car
    Given the user wants to create a Car with the following details
      | brand | model | year | price | mileage | colour |
      | Tesla |   A1  | 2001 | 195000 | 10000 | White |
    When a POST request is made to the "/cars/admin" endpoint
    Then the client receives status code of 409
    And the response body should contain the text '{"description":"Car already exists"}'

  Scenario: the client makes a GET request to the cars/admin endpoint and a list of cars are returned
    When a GET request is made to the 'cars/admin' endpoint
    Then it should return a 200 response
    And the response body should contain the cars
      | brand | model | year | price | mileage | colour |
      | Tesla |   A1  | 2001 | 195000 | 10000 | White |


  Scenario: the client makes  GET request to the cars/admin/brand endpoint and a list of matching cars are returned
    Given the user wants to create a Car with the following details
      | brand | model | year |  price | mileage | colour |
      | Tesla |   A1  | 2023 | 45837 | 10400 | White |
      | Tesla |   A1  | 2019 | 25100 | 10080 | White |
      |   BMW   | A1 |  2015  |  45032  | 10000 | White  |
      |   BMW   | X5 |  2021  |  27002  | 4572 | Black  |
    When a GET request is made to the 'cars/admin/?brand=BMW' endpoint
    Then it should return a 200 response
    And the response body should contain the cars
      | brand | model | year | price | mileage | colour |
      |   BMW   | A1 |  2015  |  45032  | 10000 | White  |
      |   BMW   | X5 |  2021  |  27002  | 4572 | Black  |

