import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NineGagSafariTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new SafariDriver();  // Safari browser
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void searchComputerAndCheckHeader() {
        driver.get("https://9gag.com");

        // Handle cookies popup if present
        try {
            WebElement acceptBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("button[id*='cookie']"))
            );
            acceptBtn.click();
        } catch (Exception ignored) {}

        // Click on Search button
        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a.search"))
        );
        searchButton.click();

        // Wait for the dynamic input field to appear
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']"))
        );

        // Enter search term
        searchInput.sendKeys("computer" + Keys.ENTER);

        // Check if header is displayed
        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("top-navb"))
        );

        Assertions.assertTrue(header.isDisplayed(), "Header not displayed!");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
