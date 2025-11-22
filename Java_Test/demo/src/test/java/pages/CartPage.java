package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators for Minicart Pop-up
    private By minicartContainer = By.id("PPMiniCart");
    private By minicartItems = By.cssSelector(".minicart-item");
    private By minicartNames = By.cssSelector(".minicart-name");
    private By minicartPrices = By.cssSelector(".minicart-price");
    private By minicartQuantities = By.cssSelector(".minicart-quantity");
    private By minicartRemoveButtons = By.cssSelector(".minicart-remove");
    private By minicartSubtotal = By.cssSelector(".minicart-subtotal");
    private By minicartSubmitButton = By.cssSelector(".minicart-submit");
    private By minicartCloser = By.cssSelector(".minicart-closer");
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isMinicartVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(minicartContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getMinicartItemsCount() {
        try {
            List<WebElement> items = driver.findElements(minicartItems);
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public boolean isProductInMinicart(String productName) {
        try {
            List<WebElement> names = driver.findElements(minicartNames);
            for (WebElement name : names) {
                if (name.getText().contains(productName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPriceOfFirstItem() {
        try {
            List<WebElement> prices = driver.findElements(minicartPrices);
            if (!prices.isEmpty()) {
                return prices.get(0).getText();
            }
            return "$0.00";
        } catch (Exception e) {
            return "$0.00";
        }
    }
    
    public void updateQuantityOfFirstItem(String quantity) {
        try {
            List<WebElement> quantities = driver.findElements(minicartQuantities);
            if (!quantities.isEmpty()) {
                quantities.get(0).clear();
                quantities.get(0).sendKeys(quantity);
            }
        } catch (Exception e) {
            // Element not found
        }
    }
    
    public String getQuantityOfFirstItem() {
        try {
            List<WebElement> quantities = driver.findElements(minicartQuantities);
            if (!quantities.isEmpty()) {
                return quantities.get(0).getAttribute("value");
            }
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }
    
    public void removeFirstItem() {
        try {
            List<WebElement> removeButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(minicartRemoveButtons));
            if (!removeButtons.isEmpty()) {
                removeButtons.get(0).click();
            }
        } catch (Exception e) {
            // Button not found
        }
    }
    
    public String getMinicartSubtotal() {
        try {
            WebElement subtotal = wait.until(ExpectedConditions.visibilityOfElementLocated(minicartSubtotal));
            return subtotal.getText();
        } catch (Exception e) {
            return "$0.00 USD";
        }
    }
    
    public void clickCheckout() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(minicartSubmitButton)).click();
        } catch (Exception e) {
            // Button not found
        }
    }
    
    public void closeMinicart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(minicartCloser)).click();
        } catch (Exception e) {
            // Button not found
        }
    }
    
    public boolean isCartEmpty() {
        try {
            List<WebElement> items = driver.findElements(minicartItems);
            return items.size() == 0;
        } catch (Exception e) {
            return true;
        }
    }
    
    public void navigateToCart() {
        // Cart opens as a minicart pop-up on the same page
        // Just ensure the driver is on the homepage
        driver.get("https://loving-hermann-e2094b.netlify.app");
        // Cart should already be visible as a pop-up
    }
    
    public int getCartItemsCount() {
        return getMinicartItemsCount();
    }
    
    public boolean isProductInCart(String productName) {
        return isProductInMinicart(productName);
    }
    
    public void increaseQuantityOfFirstItem() {
        // Minicart doesn't have increase/decrease buttons, must use input field
        try {
            List<WebElement> quantities = driver.findElements(minicartQuantities);
            if (!quantities.isEmpty()) {
                String currentQty = quantities.get(0).getAttribute("value");
                int newQty = Integer.parseInt(currentQty) + 1;
                quantities.get(0).clear();
                quantities.get(0).sendKeys(String.valueOf(newQty));
            }
        } catch (Exception e) {
            // Element not found
        }
    }
    
    public void decreaseQuantityOfFirstItem() {
        // Minicart doesn't have increase/decrease buttons, must use input field
        try {
            List<WebElement> quantities = driver.findElements(minicartQuantities);
            if (!quantities.isEmpty()) {
                String currentQty = quantities.get(0).getAttribute("value");
                int newQty = Integer.parseInt(currentQty) - 1;
                if (newQty > 0) {
                    quantities.get(0).clear();
                    quantities.get(0).sendKeys(String.valueOf(newQty));
                }
            }
        } catch (Exception e) {
            // Element not found
        }
    }
    
    public String getTotalPrice() {
        return getMinicartSubtotal();
    }
}