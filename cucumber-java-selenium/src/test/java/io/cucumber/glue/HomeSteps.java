package io.cucumber.glue;

import io.cucumber.core.*;
import io.cucumber.datatable.DataTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomeSteps extends Context {

    public HomeSteps(Manager manager) {
        super(manager);
    }

    @Given("Navigate to Example '(.+)'$")
    public void exampleHomePageStep(String exampleName) {
        WebElement exampleLink = getDriver().findElement(By.xpath("//a[text()='" + exampleName + "']"));

        exampleLink.click();
    }

    @And("Navigate to Basic Authentication with valid credentials")
    public void navigateToBasicAuthWithValidCredentials() {
        String username = "admin";
        String password = "admin";
        String url = String.format("https://%s:%s@the-internet.herokuapp.com/basic_auth", username, password);
        getDriver().get(url);
    }

    @And("valid credentials are supplied")
    public void validCredentialsAreSupplied() {
        System.out.println("Valid credentials are supplied");
        System.out.println("printing shared stash :" + getTestStash().toString());
    }

    @Then("Congratulations should be displayed")
    public void congratulationsShouldBeDisplayed() {
        WebElement message = getDriver().findElement(By.cssSelector("p"));
        String expectedMessage = "Congratulations! You must have the proper credentials.";
        if (!message.getText().equals(expectedMessage)) {
            throw new AssertionError("Expected message: " + expectedMessage + ", but got: " + message.getText());
        }
    }

    @Then("Verify that the homepage has a list of links to Expected examples")
    public void verifyHomepageLinks() {
        try {
            List<String> rows = new ArrayList<String>();
            BufferedReader r = new BufferedReader(new FileReader("src/test/resources/expected_links.csv"));
            
            rows = Arrays.asList(r.readLine().split(","));

            r.close();

            for (String linkText : rows) {
                WebElement link = getDriver().findElement(By.xpath("//a[text()='" + linkText + "']"));
                if (link == null) {
                    throw new AssertionError("Expected link not found: " + linkText);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read expected_links.csv", e);
        }
    }

    @Then("Verify the Example 1 table values displays the expected results")
    public void verifyExampleTableValues(DataTable table){
        List<Map<String, String>> expectedRows = table.asMaps(String.class, String.class);
        
        List<WebElement> actualRows = getDriver().findElements(By.cssSelector("#table1 tbody tr"));

        for (int i = 0; i < expectedRows.size(); i++) {
            Map<String, String> expectedRow = expectedRows.get(i);
            List<WebElement> actualCells = actualRows.get(i).findElements(By.cssSelector("td"));
            int cellIndex = 0;
            for (String column : expectedRow.keySet()) {
                String expectedValue = expectedRow.get(column);
                String actualValue = actualCells.get(cellIndex).getText();
                if (!expectedValue.equals(actualValue)) {
                    throw new AssertionError("Expected value in row " + (i + 1) + ", column '" + column + "': " + expectedValue + ", but got: " + actualValue);
                }
                cellIndex++;
            }
        }
    }
}
