Feature: Testing car endpoints
  Scenario: the client makes a POST request to the cars/admin endpoint
    Given the client calls POST 'cars/admin' for the following data:
    | brand | model | price | year | mileage | colour |
    | BMW | X5 | 10000 | 2010 | 100000 | black |
    When the client calls POST 'cars/admin'
    Then the client receives status code of 201
