@REGRESSION
Feature: Music Streaming App Homepage
  As a music lover, I want to be able to access and explore music easily through a music streaming app.
  The homepage of the app should display 9 albums on the main page and
  have a left sidebar with links to Search, Browse, Your Music, and Edit User profile options.


  Background:
    Given the user is on the login page
    And the user enters valid username and password
    And the user should not be directed to the personal dashboard

  @smoke @regression
  Scenario: User opens the app and sees the welcome message

    Then the user should see the welcome message

  @smoke @flaky
  Scenario: Verify homepage links

    Then the user should see the homepage links



  Scenario: Verify homepage default albums
    Then the user should see 9 recommended albums

  @albums @regression
  Scenario: Default albums

    Then the following recommended albums should be displayed
      | Cruel Summer        |
      | Fenix               |
      | Werk                |
      | Marisa              |
      | Escape              |
      | Ultimatum           |
      | Oscillation         |
      | Clouds              |
      | I am Sasha Fierce   |
