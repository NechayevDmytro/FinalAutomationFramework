Feature: Validate booking API

  Background:
    Given I get token
    When I send API request to create a booking

  Scenario: validate booking creation endpoint
    Then I check existence of the booking

  Scenario: validate booking fully updating endpoint
    And I send API request to fully update the booking
    Then I check fields are updated

  Scenario: validate booking partially updating endpoint
    And I send API request to partially update the booking
    Then I check a field is updated

  Scenario: validate booking deleting endpoint
    And I delete created booking
    Then previously created booking is deleted