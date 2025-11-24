package steps;

import utils.DriverManager;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class GenericSteps {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    public GenericSteps() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedText) {
        wait.until(ExpectedConditions.titleContains(expectedText));
        assertTrue("Page title should contain: " + expectedText,
            driver.getTitle().toLowerCase().contains(expectedText.toLowerCase()));
    }
    
    @Then("the element with {string} should be visible")
    public void the_element_with_should_be_visible(String locatorType) {
        // Generic method to verify element visibility
        // Implementation depends on your locator strategy
    }
    
    @Then("I should be redirected to a page containing {string}")
    public void i_should_be_redirected_to_a_page_containing(String expectedContent) {
        wait.until(ExpectedConditions.urlContains(expectedContent.toLowerCase().replace(" ", "")));
        assertTrue("URL should contain reference to: " + expectedContent,
            driver.getCurrentUrl().toLowerCase().contains(expectedContent.toLowerCase().replace(" ", "")));
    }
}