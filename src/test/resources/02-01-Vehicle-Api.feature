Feature: Vehicle API test

  Background:
    Given the system has started up
    Given and the database is empty

  Scenario: Adding a new Vehicle then retrieving it by its id
    When a new Vehicle with below details is added
      | make  | model  | version | doors | grossPrice | nettPrice | hp  |
      | Lexus | IS220d | Sport   | 4     | 44285      | 28488.66  | 177 |
    Then this last saved Vehicle should be retrieved by its generated id
    And the Vehicle details should match

  Scenario: Adding a new Vehicle then deleting it
    When a new Vehicle with below details is added
      | make  | model  | version | doors | grossPrice | nettPrice | hp  |
      | Lexus | IS220d | Sport   | 4     | 44285      | 28488.66  | 177 |
    Then deleting this Vehicle
    Then this last saved Vehicle should be retrieved by its generated id
    Then a Vehicle error should be returned with message containing: Vehicle not found with id: and status code: 404

  Scenario: Adding a new Vehicle with missing mandatory data should fail to validate
    When a new Vehicle with below details is added
      | make  | version | doors | grossPrice | nettPrice | hp  |
      | Lexus | Sport   | 4     | 44285      | 28488.66  | 177 |
    Then a Vehicle error should be returned with message containing: Validation failed for argument and status code: 400
