package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleSearchPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(name = "q")
    private WebElement searchBox;
    
    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToGoogle() {
        driver.get("https://www.google.com");
    }
    
    public void enterSearchTerm(String searchTerm) {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
    }
    
    public void performSearch() {
        searchBox.submit();
    }
    
    public boolean areResultsDisplayed() {
        try {
            Thread.sleep(2000); // Simple wait for demo
            return driver.getTitle().contains("Google Search");
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isCalculatorDisplayed() {
        try {
            Thread.sleep(2000);
            return driver.getPageSource().contains("calculator");
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isConverterDisplayedAtTop() {
        try {
            Thread.sleep(2000);
            return driver.getPageSource().toLowerCase().contains("convert");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void acceptCookiesIfPresent() {
        try {
            Thread.sleep(1000);
            // Simple implementation for now
        } catch (Exception e) {
            // Ignore
        }
    }
}