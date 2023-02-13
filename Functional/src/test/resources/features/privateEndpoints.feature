Feature: testingPrivateEndpoints
  Scenario: the client makes a request to the private endpoint and an OK response is returned
    When a GET request is made to the 'private/status' endpoint
    Then it should return a 200 response
    And the response body should contain the text 'OK'

  Scenario: the client makes a request to the private endpoint and a CREATED response is returned
    Given the client adds a new car using the following data:
      | brand | model | price | year | mileage | colour |
      | BMW | X5 | 10000 | 2010 | 100000 | black |
    When a POST request is made to the 'cars/admin' endpoint
    Then it should return a 201 response
    And the response body should contain the text 'CREATED'