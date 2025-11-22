import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YouTubeSafariTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new SafariDriver();  // Safari browser
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void searchComputerAndCheckHeader() {
        driver.get("https://www.youtube.com");

        // Handle cookies popup if present
        try {
            WebElement acceptBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("button[id*='cookie']"))
            );
            acceptBtn.click();
        } catch (Exception ignored) {}

        // Wait for the search input field to appear (YouTube search)
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("search_query"))
        );

        // Enter search term and press Enter
        searchInput.sendKeys("computer" + Keys.ENTER);

        // Wait for the results header or section to appear
        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("container")) // sau alt id relevant
        );

        Assertions.assertTrue(header.isDisplayed(), "Header not displayed!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
