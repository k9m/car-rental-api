Feature: Lease API test

  Background:
    Given the system has started up
    Given and the database is empty

  Scenario: Creating a new Lease then retrieving it by its id
    When a new Customer with below details is added
      | name         | street        | houseNumber | zipcode | place     | email                 | phoneNumber |
      | Johan Droost | Kasperskylaan | 123         | 1011XC  | Amsterdam | johan.droost@test.com | 0612345678  |
    When a new Vehicle with below details is added
      | make  | model  | version | doors | grossPrice | nettPrice | hp  |
      | Lexus | IS220d | Sport   | 4     | 88000      | 63000.00  | 177 |
    Then creating a Lease with below details
      | startDate  | interestRate | durationMonths | mileagePerYear |
      | 2020-01-01 | 4.5          | 60             | 45000          |
    Then the created Lease should have these details
      | startDate  | interestRate | durationMonths | mileagePerYear | leaseRate |
      | 2020-01-01 | 4.5          | 60             | 45000          | 239.82     |

