package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class Hooks {
    
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("===================================");
        System.out.println("Pornire scenariu: " + scenario.getName());
        System.out.println("===================================");
        
        // Inițializează driver-ul și navighează la homepage
        WebDriver driver = DriverManager.getDriver();
        
        try {
            System.out.println("🌐 Navigare la homepage: https://loving-hermann-e2094b.netlify.app");
            driver.get("https://loving-hermann-e2094b.netlify.app");
            
            // Așteaptă ca pagina să se încarce
            Thread.sleep(2000);
            
            String currentUrl = driver.getCurrentUrl();
            System.out.println("✅ Pagina curentă: " + currentUrl);
            
            if (currentUrl.equals("data:,") || currentUrl.isEmpty()) {
                System.err.println("❌ EROARE: Browser-ul nu a navigat la URL!");
                System.err.println("❌ URL curent: " + currentUrl);
                throw new RuntimeException("Navigarea la homepage a eșuat!");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Eroare la așteptarea încărcării paginii");
        } catch (Exception e) {
            System.err.println("❌ Eroare la navigarea către homepage: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Capturare screenshot in caz de esec
        if (scenario.isFailed()) {
            System.out.println("❌ Scenariul a eșuat! Capturare screenshot...");
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot la esec");
                System.out.println("✅ Screenshot capturat cu succes");
            } catch (Exception e) {
                System.err.println("❌ Nu s-a putut captura screenshot: " + e.getMessage());
            }
        }
        
        System.out.println("===================================");
        System.out.println("Finalizare scenariu: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());
        System.out.println("===================================");
        
        // Adaugă o pauză înainte de închidere pentru debugging (opțional)
        String pauseBeforeClose = System.getProperty("pauseBeforeClose", "0");
        if (!pauseBeforeClose.equals("0")) {
            try {
                int seconds = Integer.parseInt(pauseBeforeClose);
                System.out.println("⏸️  Pauză " + seconds + " secunde înainte de închidere...");
                Thread.sleep(seconds * 1000L);
            } catch (Exception e) {
                // Ignoră erori de parsing
            }
        }
        
        // Închidere browser după fiecare scenariu
        DriverManager.quitDriver();
    }
    
    @Before("@login")
    public void setUpLogin() {
        System.out.println("📋 Setup specific pentru teste de login");
        // Aici poți adăuga logică specifică pentru teste de login
    }
    
    @After("@cart")
    public void tearDownCart() {
        System.out.println("🛒 Cleanup specific pentru teste de cart");
        // Aici poți adăuga logică de cleanup pentru coșul de cumpărături
    }
}