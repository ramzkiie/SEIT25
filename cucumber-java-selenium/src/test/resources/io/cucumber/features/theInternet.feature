Feature: The Internet
  This feature covers (some) Example pages on 'the-internet.herokuapp.com'

  @TEST_TI_0001 @scenario1 @additionalTagRamdale
  Scenario: Homepage has a list of links to Expected examples
    Given the page under test is 'https://the-internet.herokuapp.com'
    Then Verify that the homepage has a list of links to Expected examples

  @TEST_TI_0001 @scenario2 @additionalTagRamdale
  Scenario: Basic Auth allows validated access
    Given the page under test is 'https://the-internet.herokuapp.com'
    And Navigate to Basic Authentication with valid credentials
    Then Congratulations should be displayed

  @TEST_TI_0001 @scenario3 @additionalTagRamdale
  Scenario: Sortable Data Tables - Example 1 displays the expected 4 results
    Given the page under test is 'https://the-internet.herokuapp.com'
    And Navigate to Example 'Sortable Data Tables'
    Then Verify the Example 1 table values displays the expected results
      | Last Name | First Name | Email                 | Due     | Web Site                 |
      | Smith     | John       | jsmith@gmail.com      | $50.00  | http://www.jsmith.com   |
      | Bach      | Frank      | fbach@yahoo.com       | $51.00  | http://www.frank.com     |
      | Doe       | Jason      | jdoe@hotmail.com      | $100.00 | http://www.jdoe.com      |
      | Conway    | Tim        | tconway@earthlink.net | $50.00  | http://www.timconway.com |