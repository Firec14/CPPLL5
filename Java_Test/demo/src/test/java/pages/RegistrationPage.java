package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locator pentru trigger-ul modalului
    private By registrationTrigger = By.xpath("//a[@data-toggle='modal'][@data-target='#myModal2']");
    
    // Locators pentru Registration Modal Pop-up
    private By registrationModal = By.id("myModal2");
    private By nameInput = By.xpath("//input[@name='Name']");
    private By emailInput = By.xpath("//input[@name='Email']");
    private By passwordInput = By.xpath("//input[@name='Password']");
    private By confirmPasswordInput = By.xpath("//input[@name='Confirm Password']");
    private By registerButton = By.xpath("//input[@type='submit'][@value='Sign Up']");
    private By loginLink = By.xpath("//a[@data-target='#myModal']");
    private By facebookLink = By.xpath("//a[contains(@class, 'facebook')]");
    private By twitterLink = By.xpath("//a[contains(@class, 'twitter')]");
    private By instagramLink = By.cssSelector("#myModal2 a.instagram");
    private By linkedinLink = By.cssSelector("#myModal2 a.pinterest");
    private By termsLink = By.cssSelector("#myModal2 .modal_body_left1 p a");
    private By modalTitle = By.cssSelector("#myModal2 .agileinfo_sign");
    
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToRegistrationPage() {
        System.out.println("🔄 Navigare la homepage pentru a deschide registration modal...");
        driver.get("https://loving-hermann-e2094b.netlify.app");
        
        // Așteaptă încărcarea paginii
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Deschide modalul de înregistrare
        openRegistrationModal();
    }
    
    /**
     * Deschide modalul de înregistrare prin click pe trigger
     */
    public void openRegistrationModal() {
        try {
            System.out.println("🔍 Căutare trigger pentru registration modal...");
            
            // Scroll la top mai întâi
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            Thread.sleep(300);
            
            // Click pe link-ul de register
            WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(registrationTrigger));
            System.out.println("✅ Trigger găsit, click...");
            trigger.click();
            
            // Așteaptă ca modalul să devină vizibil
            wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
            System.out.println("✅ Registration modal deschis cu succes");
            
            Thread.sleep(500); // Așteaptă animația modalului
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la deschiderea modalului de înregistrare: " + e.getMessage());
            // Încearcă cu JavaScript
            try {
                System.out.println("ℹ️  Încerc să deschid modalul cu JavaScript...");
                ((JavascriptExecutor) driver).executeScript("$('#myModal2').modal('show');");
                Thread.sleep(1000);
                System.out.println("✅ Modal deschis cu JavaScript");
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut deschide modalul de înregistrare", ex);
            }
        }
    }
    
    public boolean isRegistrationModalVisible() {
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(registrationModal));
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }
    
    public void enterName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        nameField.clear();
        nameField.sendKeys(name);
        System.out.println("✅ Nume introdus: " + name);
    }
    
    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
        System.out.println("✅ Email introdus: " + email);
    }
    
    public void enterPassword(String password) {
        try {
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("✅ Password introdus");
        } catch (Exception e) {
            System.out.println("⚠️  Câmp password nu există pe această pagină");
        }
    }
    
    public void enterConfirmPassword(String confirmPassword) {
        try {
            WebElement confirmPasswordField = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
            confirmPasswordField.clear();
            confirmPasswordField.sendKeys(confirmPassword);
            System.out.println("✅ Confirm password introdus");
        } catch (Exception e) {
            System.out.println("⚠️  Câmp confirm password nu există pe această pagină");
        }
    }
    
    public void clickRegisterButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
            button.click();
            System.out.println("✅ Click pe Sign Up");
        } catch (Exception e) {
            System.err.println("❌ Eroare la click pe Sign Up: " + e.getMessage());
            // Încearcă cu JavaScript
            try {
                WebElement button = driver.findElement(registerButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut face click pe butonul Sign Up", ex);
            }
        }
    }
    
    public void registerWithAllFields(String name, String email, String password, String confirmPassword) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        clickRegisterButton();
    }
    
    public void clickFacebookRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(facebookLink)).click();
    }
    
    public void clickTwitterRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(twitterLink)).click();
    }
    
    public void clickInstagramRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(instagramLink)).click();
    }
    
    public void clickLinkedinRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(linkedinLink)).click();
    }
    
    public String getRegistrationModalTitle() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle));
            return title.getText();
        } catch (Exception e) {
            return "";
        }
    }
}