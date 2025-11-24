package steps;

import pages.GoogleSearchPage;
import utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;

public class GoogleSearchSteps {
    
    private WebDriver driver;
    private GoogleSearchPage googleSearchPage;
    
    public GoogleSearchSteps() {
        this.driver = DriverManager.getDriver();
        this.googleSearchPage = new GoogleSearchPage(driver);
    }
    
    @Given("I am on the Google search page")
    public void i_am_on_the_google_search_page() {
        googleSearchPage.navigateToGoogle();
        googleSearchPage.acceptCookiesIfPresent();
    }
    
    @When("I search for {string} in English")
    public void i_search_for_in_english(String searchTerm) {
        googleSearchPage.enterSearchTerm(searchTerm);
        googleSearchPage.performSearch();
    }
    
    @When("I search for {string} in Spanish")
    public void i_search_for_in_spanish(String searchTerm) {
        i_search_for_in_english(searchTerm);
    }
    
    @When("I search for {string} in French")
    public void i_search_for_in_french(String searchTerm) {
        i_search_for_in_english(searchTerm);
    }
    
    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        googleSearchPage.enterSearchTerm(searchTerm);
        googleSearchPage.performSearch();
    }
    
    @Then("I should see relevant results for {string}")
    public void i_should_see_relevant_results_for(String expectedTerm) {
        Assert.assertTrue("Search results should be displayed", 
            googleSearchPage.areResultsDisplayed());
    }
    
    @Then("I save the number of results")
    public void i_save_the_number_of_results() {
        // Implementation for saving results count
    }
    
    @Then("the number of results should be similar to saved results")
    public void the_number_of_results_should_be_similar_to_saved_results() {
        // Implementation for comparing results
    }
    
    @Then("I should see the calculator service displayed")
    public void i_should_see_the_calculator_service_displayed() {
        Assert.assertTrue("Calculator service should be displayed", 
            googleSearchPage.isCalculatorDisplayed());
    }
    
    @Then("the conversion service should appear at the top of search results")
    public void the_conversion_service_should_appear_at_the_top_of_search_results() {
        Assert.assertTrue("Converter service should be displayed at top", 
            googleSearchPage.isConverterDisplayedAtTop());
    }
}