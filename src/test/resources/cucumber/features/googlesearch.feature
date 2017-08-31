Feature: User searching some information


  @negative
  Scenario: User can search
    Given user has opened Main Page successfully
    When user searches 'data'
    Then user see results