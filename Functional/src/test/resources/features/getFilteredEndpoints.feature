Feature: Car Get Filtered List
  Background:
  Given the user wants to create a Car with the following details
  | brand | model | year |  price | mileage | colour |
  | Tesla |   A1  | 2023 | 45837 | 10400 | White |
  | Tesla |   A1  | 2019 | 25100 | 10080 | White |
  |   BMW   | A1 |  2015  |  45032  | 10000 | White  |
  |   BMW   | X5 |  2021  |  27002  | 4572 | Black  |

  Scenario: the client makes  GET request to the cars/admin/brand endpoint and a list of matching cars are returned
  When a GET request is made to the 'cars/admin?brand=BMW' endpoint
  Then it should return a 200 response
  And the response body should contain the cars
  | brand | model | year | price | mileage | colour |
  |   BMW   | A1 |  2015  |  45032  | 10000 | White  |
  |   BMW   | X5 |  2021  |  27002  | 4572 | Black  |

  Scenario: the client makes  GET request to the cars/admin/model endpoint and a list of matching cars are returned
  When a GET request is made to the 'cars/admin?model=A1' endpoint
  Then it should return a 200 response
  And the response body should contain the cars
  | brand | model | year | price | mileage | colour |
  | Tesla |   A1  | 2023 | 45837 | 10400 | White |
  | Tesla |   A1  | 2019 | 25100 | 10080 | White |
  |   BMW   | A1 |  2015  |  45032  | 10000 | White  |