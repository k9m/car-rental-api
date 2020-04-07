Feature: Customer API test

  Background:
    Given the system has started up
    Given and the database is empty

  Scenario: Adding a new Customer then retrieving it by its id
    When a new Customer with below details is added
      | name         | street        | houseNumber | zipcode | place     | email                 | phoneNumber |
      | Johan Droost | Kasperskylaan | 123         | 1011XC  | Amsterdam | johan.droost@test.com | 0612345678  |
    Then this last saved Customer should be retrieved by its generated id
    And the Customer details should match

  Scenario: Adding a new Customer then deleting it
    When a new Customer with below details is added
      | name         | street        | houseNumber | zipcode | place     | email                 | phoneNumber |
      | Johan Droost | Kasperskylaan | 123         | 1011XC  | Amsterdam | johan.droost@test.com | 0612345678  |
    Then deleting this customer
    Then this last saved Customer should be retrieved by its generated id
    Then an error should be returned with message containing: Customer not found with id: and status code: 404

  Scenario: Adding a new Customer with missing mandatory data should fail to validate
    When a new Customer with below details is added
      | name         | street        | houseNumber | zipcode | place     | email                 |
      | Johan Droost | Kasperskylaan | 123         | 1011XC  | Amsterdam | johan.droost@test.com |
    Then an error should be returned with message containing: Validation failed for argument and status code: 400
