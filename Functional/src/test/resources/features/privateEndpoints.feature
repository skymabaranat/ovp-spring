Feature: testingPrivateEndpoints
  Scenario: the client makes a request to the private endpoint and an OK response is returned
    When a GET request is made to the 'private/status' endpoint
    Then it should return a 200 response
    And the response body should contain the text 'OK'
