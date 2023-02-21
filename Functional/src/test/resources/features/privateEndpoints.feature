Feature: testingPrivateEndpoints
#  Background:
#    Given the user wants to create a Car with the following details
#      | brand | model | price | year | mileage | colour |
#      | Tesla | A1 | 1213 | 2010 | 10000 | White |

  Scenario: the client makes a request to the private endpoint and an OK response is returned
    When a GET request is made to the 'private/status' endpoint
    Then it should return a 200 response
    And the response body should contain the text 'OK'

  Scenario: the client makes a GET request to the cars/admin endpoint and a list of cars are returned
    Given the user wants to create a Car with the following details
      | brand | model | year | price | mileage | colour |
      | Tesla | A1 | 2022 | 195000 | 350 | White |
    When a GET request is made to the 'cars/admin' endpoint
    Then it should return a 200 response
    And the response body should contain the cars
      | brand | model | year | price | mileage | colour |
      | Tesla | A1 | 2022 | 195000 | 350 | White |
