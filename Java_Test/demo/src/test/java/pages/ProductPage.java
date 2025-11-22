package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By sortDropdown = By.id("country1");
    private By productItems = By.cssSelector(".product-item, [class*='product'], li.product");
    private By productTitles = By.cssSelector(".product-item h3, [class*='product'] h3, .product-name");
    private By productPrices = By.cssSelector(".product-item .price, [class*='product'] .price, .product-price");
    private By addToCartButtons = By.xpath("//input[@type='submit'][@name='submit'][@value='Add to cart']");
    private By cartIcon = By.cssSelector(".cart-icon, [class*='cart'], .minicart");
    private By cartBadge = By.cssSelector(".cart-badge, [class*='badge']");
    
    // Review locators - multiple variante
    private By addReviewButton = By.cssSelector(".add-review, button.add-review, a.add-review");
    private By reviewsTab = By.xpath("//li[@role='tab' and contains(text(), 'Reviews')]");
    private By reviewNameInput = By.xpath("//input[@name='Name']");
    private By reviewEmailInput = By.xpath("//input[@name='Email']");
    private By reviewTextarea = By.xpath("//textarea[@name='Message']");
    private By reviewSubmitButton = By.xpath("//input[@type='submit'][@value='SEND']");
    private By reviewsList = By.cssSelector(".reviews-list .review-item, [class*='review']");
    
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToProductPage() {
        System.out.println("🔄 Navigare la pagina de produse...");
        driver.get("https://loving-hermann-e2094b.netlify.app/mens");
        wait.until(ExpectedConditions.presenceOfElementLocated(productItems));
        try {
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(sortDropdown));
            System.out.println("✅ Sort dropdown găsit pe pagina de produse");
            
            // Scroll la dropdown pentru a-l face vizibil
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                dropdown
            );
            Thread.sleep(500);
            System.out.println("✅ Scroll la sort dropdown completat");
        } catch (Exception e) {
            System.out.println("⚠️  Sort dropdown nu s-a găsit, continuând fără el");
        }
        System.out.println("✅ Pagina de produse încărcată");
    }
    
    public void navigateToSingleProductPage() {
        System.out.println("🔄 Navigare la pagina single product...");
        driver.get("https://loving-hermann-e2094b.netlify.app/single");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Add to cart']")));
        System.out.println("✅ Pagina single product încărcată");
    }
    
    public void selectSortOption(String sortOption) {
        try {
            System.out.println("🔍 Căutare dropdown sort cu id='country1'...");
            
            // Scroll to top first
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
            Thread.sleep(500);
            
            // Wait longer for dropdown with extended timeout
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for dropdown to be present
            WebElement dropdown = longWait.until(ExpectedConditions.presenceOfElementLocated(sortDropdown));
            System.out.println("✅ Dropdown găsit, scrolling la el...");
            
            // Scroll to dropdown and wait for it to be clickable
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                dropdown
            );
            Thread.sleep(1500);
            
            // Re-find dropdown to ensure it's still available
            dropdown = longWait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
            System.out.println("✅ Dropdown este clickable");
            
            // Use Select to choose option
            Select select = new Select(dropdown);
            
            // Map user-friendly text to option values
            String optionValue = mapSortOption(sortOption);
            System.out.println("Selectare cu valoare: " + optionValue);
            
            select.selectByValue(optionValue);
            System.out.println("✅ Sort option selectat: " + sortOption);
            
            // Wait for page to reload with sorted results
            Thread.sleep(2000);
            
        } catch (TimeoutException e) {
            System.err.println("❌ Timeout la căutare dropdown: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut selecta sort option: " + sortOption + " (Timeout)", e);
        } catch (Exception e) {
            System.err.println("❌ Eroare la selectare sort: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Nu s-a putut selecta sort option: " + sortOption, e);
        }
    }
    
    private String mapSortOption(String sortOption) {
        // Map Romanian text to option values from HTML
        // <option value="0">Name(A - Z)</option>
        // <option value="1">Name(Z - A)</option>
        // <option value="2">Price(High - Low)</option>
        // <option value="3">Price(Low - High)</option>
        
        String lower = sortOption.toLowerCase();
        if (lower.contains("mic la mare") || lower.contains("low - high")) {
            return "3";
        } else if (lower.contains("mare la mic") || lower.contains("high - low")) {
            return "2";
        } else if (lower.contains("a-z") || lower.contains("a - z")) {
            return "0";
        } else if (lower.contains("z-a") || lower.contains("z - a")) {
            return "1";
        }
        return "null"; // Default
    }
    
    public List<String> getProductTitles() {
        List<WebElement> titles = driver.findElements(productTitles);
        List<String> titlesList = new ArrayList<>();
        for (WebElement title : titles) {
            titlesList.add(title.getText());
        }
        return titlesList;
    }
    
    public List<Double> getProductPrices() {
        List<WebElement> prices = driver.findElements(productPrices);
        List<Double> pricesList = new ArrayList<>();
        for (WebElement price : prices) {
            String priceText = price.getText().replace("$", "").replace(",", "").trim();
            pricesList.add(Double.parseDouble(priceText));
        }
        return pricesList;
    }
    
    public void addFirstProductToCart() {
        try {
            List<WebElement> submitButtons = driver.findElements(By.xpath("//input[@type='submit'][@name='submit'][@value='Add to cart']"));
            if (!submitButtons.isEmpty()) {
                submitButtons.get(0).click();
                System.out.println("✅ Produs adăugat în coș");
                return;
            }
        } catch (Exception e1) {
            try {
                List<WebElement> buttons = driver.findElements(By.xpath("//input[@type='submit'][@value='Add to cart']"));
                if (!buttons.isEmpty()) {
                    buttons.get(0).click();
                    System.out.println("✅ Produs adăugat în coș (fallback)");
                    return;
                }
            } catch (Exception e2) {
                throw new RuntimeException("Nu s-a putut găsi butonul Add to Cart", e2);
            }
        }
    }
    
    public void addProductToCartByIndex(int index) {
        try {
            List<WebElement> addButtons = driver.findElements(By.xpath("//input[@type='submit'][@name='submit'][@value='Add to cart']"));
            if (index < addButtons.size()) {
                addButtons.get(index).click();
                System.out.println("✅ Produs " + index + " adăugat în coș");
            } else {
                throw new RuntimeException("Index " + index + " invalid");
            }
        } catch (Exception e) {
            throw new RuntimeException("Nu s-a putut adăuga produsul la index " + index, e);
        }
    }
    
    public String getCartBadgeCount() {
        try {
            WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
            return badge.getText();
        } catch (Exception e) {
            return "0";
        }
    }
    
    public void clickCartIcon() {
        try {
            WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
            cartBtn.click();
        } catch (Exception e) {
            try {
                List<WebElement> carts = driver.findElements(By.xpath("//*[contains(@class, 'cart')]"));
                if (!carts.isEmpty()) {
                    carts.get(0).click();
                }
            } catch (Exception e2) {
                // Cart se deschide automat
            }
        }
    }
    
    public void clickReviewButton() {
        try {
            System.out.println("🔍 Căutare tab Reviews...");
            
            // Scroll la section cu tabs
            WebElement reviewsTabElement = wait.until(ExpectedConditions.elementToBeClickable(reviewsTab));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", reviewsTabElement);
            Thread.sleep(500);
            
            // Click pe Reviews tab
            reviewsTabElement.click();
            System.out.println("✅ Reviews tab deschis");
            
            Thread.sleep(1000); // Așteaptă tab să se deschidă
            
        } catch (Exception e) {
            System.out.println("⚠️  Review form ar putea fi deja vizibil");
        }
    }
    
    public void enterReviewName(String name) {
        try {
            System.out.println("🔍 Căutare câmp Name pentru review...");
            
            // Încearcă să găsească toate input-urile de tip text cu name='Name'
            List<WebElement> nameInputs = driver.findElements(reviewNameInput);
            
            if (nameInputs.isEmpty()) {
                throw new RuntimeException("Nu s-a găsit câmpul Name pentru review");
            }
            
            // Dacă sunt multiple câmpuri Name (ex: și în contact form), ia ultimul
            WebElement nameInput = nameInputs.get(nameInputs.size() - 1);
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nameInput);
            Thread.sleep(500);
            
            // Așteaptă să fie vizibil
            wait.until(ExpectedConditions.visibilityOf(nameInput));
            
            nameInput.clear();
            nameInput.sendKeys(name);
            System.out.println("✅ Nume review introdus: " + name);
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea numelui pentru review: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce numele pentru review", e);
        }
    }
    
    public void enterReviewEmail(String email) {
        try {
            List<WebElement> emailInputs = driver.findElements(reviewEmailInput);
            
            if (emailInputs.isEmpty()) {
                throw new RuntimeException("Nu s-a găsit câmpul Email pentru review");
            }
            
            WebElement emailInput = emailInputs.get(emailInputs.size() - 1);
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailInput);
            Thread.sleep(300);
            
            emailInput.clear();
            emailInput.sendKeys(email);
            System.out.println("✅ Email review introdus: " + email);
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea emailului pentru review: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce emailul pentru review", e);
        }
    }
    
    public void enterReviewText(String reviewText) {
        try {
            System.out.println("🔍 Căutare câmp Message pentru review...");
            System.out.println("Pagina actuala: " + driver.getCurrentUrl());
            
            // Prima încercare: XPath specific
            System.out.println("Încercare 1: XPath specific //textarea[@name='Message']");
            List<WebElement> textareas = driver.findElements(reviewTextarea);
            
            if (textareas.isEmpty()) {
                System.out.println("⚠️  XPath specific nu a găsit, încerc textareas din .add-review");
                // Caută textareas în cadrul .add-review div
                textareas = driver.findElements(By.cssSelector(".add-review textarea"));
            }
            
            if (textareas.isEmpty()) {
                System.out.println("⚠️  CSS .add-review textarea nu a găsit, încerc toate textareas");
                textareas = driver.findElements(By.tagName("textarea"));
            }
            
            if (textareas.isEmpty()) {
                System.out.println("⚠️  tagName textarea nu a găsit, încerc CSS generic");
                textareas = driver.findElements(By.cssSelector("textarea"));
            }
            
            if (textareas.isEmpty()) {
                System.out.println("❌ Total: nu s-au găsit textareas pe pagina");
                throw new RuntimeException("Nu s-a găsit câmpul Message pentru review");
            }
            
            System.out.println("✅ Găsite " + textareas.size() + " textareas");
            WebElement textarea = textareas.get(0);
            
            // Scroll și focus
            System.out.println("Scroll la textarea...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", textarea);
            Thread.sleep(1000);
            
            // Verifi dacă e vizibil
            try {
                WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                longWait.until(ExpectedConditions.visibilityOf(textarea));
                System.out.println("✅ Textarea este vizibil");
            } catch (Exception e) {
                System.out.println("⚠️  Textarea nu e vizibil, dar încerc oricum: " + e.getMessage());
            }
            
            // Focus și clear
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", textarea);
            Thread.sleep(500);
            textarea.clear();
            
            // Introdu text
            textarea.sendKeys(reviewText);
            System.out.println("✅ Text review introdus: " + reviewText);
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea textului pentru review: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Nu s-a putut introduce textul pentru review", e);
        }
    }
    
    public void selectReviewRating(String rating) {
        System.out.println("⚠️  Rating nu este disponibil pe această pagină");
    }
    
    public void submitReview() {
        try {
            List<WebElement> submitButtons = driver.findElements(reviewSubmitButton);
            
            if (submitButtons.isEmpty()) {
                throw new RuntimeException("Nu s-a găsit butonul SEND pentru review");
            }
            
            WebElement submitButton = submitButtons.get(submitButtons.size() - 1);
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", submitButton);
            Thread.sleep(500);
            
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            submitButton.click();
            
            System.out.println("✅ Review trimis");
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la trimiterea review-ului: " + e.getMessage());
            // Încearcă cu JavaScript
            try {
                List<WebElement> submitButtons = driver.findElements(reviewSubmitButton);
                WebElement submitButton = submitButtons.get(submitButtons.size() - 1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
                System.out.println("✅ Review trimis (JavaScript)");
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut trimite review-ul", ex);
            }
        }
    }
    
    public boolean isReviewDisplayed(String reviewText) {
        try {
            List<WebElement> reviews = driver.findElements(reviewsList);
            for (WebElement review : reviews) {
                if (review.getText().contains(reviewText)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}