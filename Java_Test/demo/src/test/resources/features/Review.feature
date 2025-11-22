#language: en

Feature: Review products
  As an authenticated user
  I want to be able to leave reviews for products
  So I can help other users in their purchase decision

  Background:
    Given utilizatorul este pe pagina single product

  Scenario: Add review with medium rating
    When utilizatorul da click pe butonul de review
    And utilizatorul completeaza review-ul cu:
      | Text   | Produs bun, dar poate fi imbunatatit |
      | Rating | 3                                     |
    And utilizatorul trimite review-ul
    Then review-ul este vizibil in lista de review-uri

