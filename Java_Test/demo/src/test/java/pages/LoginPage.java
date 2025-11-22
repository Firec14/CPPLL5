package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators pentru trigger-ul modalului
    private By loginTrigger = By.xpath("//a[@data-toggle='modal'][@data-target='#myModal']");
    
    // Locators pentru Login Modal Pop-up
    private By loginModal = By.id("myModal");
    private By nameInput = By.xpath("//input[@name='Name']");
    private By emailInput = By.xpath("//input[@name='Email']");
    private By loginButton = By.xpath("//input[@type='submit'][@value='Sign In']");
    private By signUpLink = By.xpath("//a[@data-target='#myModal2']");
    private By instagramLink = By.cssSelector("#myModal a.instagram");
    private By linkedinLink = By.cssSelector("#myModal a.pinterest");
    private By modalTitle = By.cssSelector("#myModal .agileinfo_sign");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToLoginPage() {
        System.out.println("🔄 Navigare la homepage pentru a deschide login modal...");
        driver.get("https://loving-hermann-e2094b.netlify.app");
        
        // Așteaptă încărcarea paginii
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Deschide modalul de login
        openLoginModal();
    }
    
    /**
     * Deschide modalul de login prin click pe trigger
     */
    public void openLoginModal() {
        try {
            System.out.println("🔍 Căutare trigger pentru login modal...");
            
            // Scroll la top mai întâi
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            Thread.sleep(300);
            
            // Caută trigger-ul
            WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(loginTrigger));
            System.out.println("✅ Trigger găsit, click...");
            trigger.click();
            
            // Așteaptă ca modalul să devină vizibil
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
            System.out.println("✅ Login modal deschis cu succes");
            
            Thread.sleep(500); // Așteaptă animația modalului
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la deschiderea modalului de login: " + e.getMessage());
            // Încearcă cu JavaScript
            try {
                System.out.println("ℹ️  Încerc să deschid modalul cu JavaScript...");
                ((JavascriptExecutor) driver).executeScript("$('#myModal').modal('show');");
                Thread.sleep(1000);
                System.out.println("✅ Modal deschis cu JavaScript");
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut deschide modalul de login", ex);
            }
        }
    }
    
    public boolean isLoginModalVisible() {
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void enterName(String name) {
        try {
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
            nameField.clear();
            nameField.sendKeys(name);
            System.out.println("✅ Nume introdus: " + name);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea numelui: " + e.getMessage());
        }
    }
    
    public void enterEmail(String email) {
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("✅ Email introdus: " + email);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea emailului: " + e.getMessage());
        }
    }
    
    public void clickLoginButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            button.click();
            System.out.println("✅ Click pe Sign In");
        } catch (Exception e) {
            System.err.println("❌ Eroare la click pe Sign In: " + e.getMessage());
            // Încearcă cu JavaScript
            try {
                WebElement button = driver.findElement(loginButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut face click pe butonul Sign In", ex);
            }
        }
    }
    
    public void loginWithNameAndEmail(String name, String email) {
        enterName(name);
        enterEmail(email);
        clickLoginButton();
    }
    
    public void clickSignUpLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
    }
    
    public void clickInstagramLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(instagramLink)).click();
    }
    
    public void clickLinkedinLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(linkedinLink)).click();
    }
    
    public String getLoginModalTitle() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle));
            return title.getText();
        } catch (Exception e) {
            return "";
        }
    }
}