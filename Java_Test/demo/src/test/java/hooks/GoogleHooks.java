package hooks;

import utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class GoogleHooks {
    
    @Before("@google")
    public void setUp() {
        // Driver is initialized lazily in DriverManager
    }
    
    @After("@google")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot if scenario fails
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        DriverManager.quitDriver();
    }
}