Feature: Google Search Functionality Tests

  Scenario: Search in different languages
    Given I am on the Google search page
    When I search for "hello" in English
    Then I should see relevant results for "hello"
    When I search for "hola" in Spanish
    Then I should see relevant results for "hola"
    When I search for "bonjour" in French
    Then I should see relevant results for "bonjour"

  Scenario: Verify case sensitivity in Google Search
    Given I am on the Google search page
    When I search for "TestCase"
    Then I save the number of results
    When I search for "testcase"
    Then the number of results should be similar to saved results

  Scenario: Display calculator service when searching for "calculator"
    Given I am on the Google search page
    When I search for "calculator"
    Then I should see the calculator service displayed

  Scenario: Converter service appears at top when searching for conversion services
    Given I am on the Google search page
    When I search for "Google converter services"
    Then the conversion service should appear at the top of search results