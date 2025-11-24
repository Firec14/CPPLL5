package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initDriver();
        }
        return driver.get();
    }
    
    private static void initDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        WebDriver webDriver;
        
        switch (browser) {
            case "firefox":
                webDriver = setupFirefoxDriver(headless);
                break;
            case "edge":
                webDriver = setupEdgeDriver(headless);
                break;
            case "safari":
                webDriver = setupSafariDriver();
                break;
            case "chrome":
            default:
                webDriver = setupChromeDriver(headless);
                break;
        }
        
        configureDriver(webDriver);
        driver.set(webDriver);
    }
    
    private static WebDriver setupChromeDriver(boolean headless) {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        
        ChromeOptions chromeOptions = new ChromeOptions();
        
        // Configurații de bază
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        
        // Configurații pentru performanță și stabilitate
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
        chromeOptions.addArguments("--disable-background-timer-throttling");
        chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
        chromeOptions.addArguments("--disable-renderer-backgrounding");
        
        // Exclude automation indicator
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        
        // Preferințe pentru download (dacă este necesar)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        
        // Mod headless îmbunătățit
        if (headless) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--window-size=1920,1080");
            chromeOptions.addArguments("--disable-gpu");
        }
        
        return new ChromeDriver(chromeOptions);
    }
    
    private static WebDriver setupFirefoxDriver(boolean headless) {
        WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        
        if (headless) {
            firefoxOptions.addArguments("--headless");
        }
        
        return new FirefoxDriver(firefoxOptions);
    }
    
    private static WebDriver setupEdgeDriver(boolean headless) {
        WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
        
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--remote-allow-origins=*");
        
        if (headless) {
            edgeOptions.addArguments("--headless=new");
        }
        
        return new EdgeDriver(edgeOptions);
    }
    
    private static WebDriver setupSafariDriver() {
        WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();
        
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setAutomaticInspection(false);
        
        return new SafariDriver(safariOptions);
    }
    
    private static void configureDriver(WebDriver webDriver) {
        // Timeout-uri globale
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        
        // Delete all cookies la inițializare pentru a începe cu o sesiune curată
        webDriver.manage().deleteAllCookies();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
    
    public static void closeDriver() {
        if (driver.get() != null) {
            try {
                driver.get().close();
            } catch (Exception e) {
                System.err.println("Error while closing driver: " + e.getMessage());
            }
        }
    }
    
    // Metode utilitare suplimentare
    public static String getBrowserName() {
        return System.getProperty("browser", "chrome").toLowerCase();
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
    
    public static void setImplicitWait(int seconds) {
        if (driver.get() != null) {
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        }
    }
    
    public static void resetImplicitWait() {
        setImplicitWait(10); // Valoare implicită
    }
    
    // Metodă pentru a obține titlul paginii curente
    public static String getPageTitle() {
        return driver.get() != null ? driver.get().getTitle() : "";
    }
    
    // Metodă pentru a obține URL-ul curent
    public static String getCurrentUrl() {
        return driver.get() != null ? driver.get().getCurrentUrl() : "";
    }
    
    // Metodă pentru a naviga către un URL
    public static void navigateTo(String url) {
        if (driver.get() != null) {
            driver.get().get(url);
        }
    }
    
    // Metodă pentru a refresh pagina curentă
    public static void refreshPage() {
        if (driver.get() != null) {
            driver.get().navigate().refresh();
        }
    }
    
    // Metodă pentru a naviga înapoi
    public static void navigateBack() {
        if (driver.get() != null) {
            driver.get().navigate().back();
        }
    }
    
    // Metodă pentru a naviga înainte
    public static void navigateForward() {
        if (driver.get() != null) {
            driver.get().navigate().forward();
        }
    }
}