#language: en

Feature: Sort products
  As an online store user
  I want to be able to sort products
  So I can find them more easily

  Background:
    Given utilizatorul este pe pagina de produse

  @skip
  Scenario: Sort products by price ascending
    When utilizatorul selecteaza sortarea dupa "Price(Low - High)"
    Then produsele sunt sortate crescator dupa pret
    And ordinea produselor s-a schimbat

  @skip
  Scenario: Sort products by price descending
    When utilizatorul selecteaza sortarea dupa "Price(High - Low)"
    Then produsele sunt sortate descrescator dupa pret
    And ordinea produselor s-a schimbat

  @skip
  Scenario: Sort products alphabetically
    When utilizatorul selecteaza sortarea dupa "Name(A - Z)"
    Then produsele sunt sortate alfabetic dupa nume
    And ordinea produselor s-a schimbat

  @skip
  Scenario: Verify multiple sorts
    When utilizatorul selecteaza sortarea dupa "Price(Low - High)"
    Then produsele sunt sortate crescator dupa pret
    When utilizatorul selecteaza sortarea dupa "Price(High - Low)"
    Then produsele sunt sortate descrescator dupa pret