Feature: Validate mobile page on Rozetka

  @UI
  Scenario Outline: validate price range filtering
    Given I open Rozetka website
    And I click Smartphones and Electronics from left menu
    And I click Mobile phones
    When I set minimal price as <min_price> and maximal price as <max_price>
    Then on the result page all phones' price is inside the range

    Examples:
      | min_price | max_price |
      | 6000      | 15000     |
      | 8000      | 20000     |
      | 3000      | 7000      |