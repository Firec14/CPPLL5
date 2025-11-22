package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators pentru Contact Form pe Homepage
    private By contactForm = By.cssSelector(".contact-form, form[name='sentMessage']");
    private By nameInput = By.cssSelector("input[name='Name'], input[placeholder*='Name']");
    private By emailInput = By.cssSelector("input[name='Email'], input[placeholder*='Email']");
    private By subjectInput = By.cssSelector("input[name='Subject'], input[placeholder*='Subject']");
    private By messageTextarea = By.cssSelector("textarea[name='Message'], textarea[placeholder*='Message']");
    private By sendButton = By.cssSelector("input[type='submit'][value='SEND'], button[type='submit']");
    private By successMessage = By.cssSelector(".success-message, .alert-success");
    private By errorMessage = By.cssSelector(".error-message, .alert-danger");
    private By contactSection = By.cssSelector(".agile-contact-left, .contact-section, section.contact");
    
    public ContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToContactPage() {
        System.out.println("🔄 Navigare la homepage pentru contact form...");
        driver.get("https://loving-hermann-e2094b.netlify.app");
        
        // Așteaptă încărcarea paginii
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Scroll până la secțiunea de contact
        scrollToContactSection();
    }
    
    /**
     * Scroll până la secțiunea de contact
     */
    private void scrollToContactSection() {
        try {
            System.out.println("📜 Scroll la secțiunea de contact...");
            
            // Încearcă să găsească secțiunea de contact
            WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(contactSection));
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", section);
            Thread.sleep(1000);
            
            System.out.println("✅ Secțiune de contact vizibilă");
            
        } catch (Exception e) {
            System.out.println("⚠️  Nu s-a găsit secțiunea specifică, scroll la sfârșit de pagină...");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public boolean isContactFormVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(contactForm));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void enterName(String name) {
        try {
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nameField);
            Thread.sleep(300);
            
            nameField.clear();
            nameField.sendKeys(name);
            System.out.println("✅ Nume introdus: " + name);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea numelui: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce numele", e);
        }
    }
    
    public void enterEmail(String email) {
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
            Thread.sleep(300);
            
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("✅ Email introdus: " + email);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea emailului: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce emailul", e);
        }
    }
    
    public void enterSubject(String subject) {
        try {
            WebElement subjectField = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectInput));
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subjectField);
            Thread.sleep(300);
            
            subjectField.clear();
            subjectField.sendKeys(subject);
            System.out.println("✅ Subject introdus: " + subject);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea subiectului: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce subiectul", e);
        }
    }
    
    public void enterMessage(String message) {
        try {
            WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(messageTextarea));
            
            // Scroll la element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", messageField);
            Thread.sleep(300);
            
            messageField.clear();
            messageField.sendKeys(message);
            System.out.println("✅ Message introdus: " + message);
        } catch (Exception e) {
            System.err.println("❌ Eroare la introducerea mesajului: " + e.getMessage());
            throw new RuntimeException("Nu s-a putut introduce mesajul", e);
        }
    }
    
    public void clickSendButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(sendButton));
            
            // Scroll la buton
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(500);
            
            // Așteaptă să fie clickable
            wait.until(ExpectedConditions.elementToBeClickable(sendButton));
            
            button.click();
            System.out.println("✅ Click pe SEND");
            
        } catch (Exception e) {
            System.err.println("❌ Eroare la click pe SEND: " + e.getMessage());
            // Încearcă cu JavaScript click
            try {
                WebElement button = driver.findElement(sendButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("✅ Click pe SEND (JavaScript)");
            } catch (Exception ex) {
                throw new RuntimeException("Nu s-a putut face click pe butonul SEND", ex);
            }
        }
    }
    
    public void fillContactForm(String name, String email, String subject, String message) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
    }
    
    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return success.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getSuccessMessage() {
        try {
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return success.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.getText();
        } catch (Exception e) {
            return "";
        }
    }
}