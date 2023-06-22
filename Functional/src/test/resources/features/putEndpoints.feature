Feature:
  Background:
    Given there exists a Car in the database with the following details
      |id| brand | model | year |  price | mileage | colour |
      |123abc| Tesla |   A1  | 2019 | 25100 | 10080 | White |
      |789xyz|  BMW  | A1 |  2015  |  45032  | 10000 | White  |

    Scenario: the client makes a PUT request to the cars/admin/brand endpoint
      When a PUT request is made to the "cars/admin?id=123abc" endpoint with the request body
        | brand | model | year |  price | mileage | colour |
        | Tesla | ModelX  | 2022 | 25100 | 10080 | White |
      Then it should return a 200 response
      And the response body should contain the text '{"description":"Car updated"}'
