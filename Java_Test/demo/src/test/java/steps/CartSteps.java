package steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import pages.ProductPage;
import pages.CartPage;
import utils.DriverManager;

public class CartSteps {
    private ProductPage productPage;
    private CartPage cartPage;
    
    public CartSteps() {
        this.productPage = new ProductPage(DriverManager.getDriver());
        this.cartPage = new CartPage(DriverManager.getDriver());
    }
    
    @Given("utilizatorul se afla pe pagina de produse pentru cos")
    public void utilizatorulSeAflaLaPaginaDeProdusePentruCos() {
        System.out.println("🔄 Navigare la pagina de produse...");
        productPage.navigateToProductPage();
        
        // Verifică că pagina s-a încărcat corect
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        System.out.println("✅ URL curent: " + currentUrl);
        assertTrue(currentUrl.contains("loving-hermann-e2094b.netlify.app"), 
            "URL-ul trebuie să conțină loving-hermann-e2094b.netlify.app");
    }
    
    @When("utilizatorul adauga primul produs in cos")
    public void utilizatorulAdaugaPrimulProdusInCos() {
        System.out.println("🛒 Adăugare produs în coș...");
        productPage.addFirstProductToCart();
        
        try {
            Thread.sleep(2000); // Așteaptă ca produsul să fie adăugat și minicart să apară
            System.out.println("✅ Produs adăugat în coș");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
    
    @When("utilizatorul deschide cosul")
    public void utilizatorulDeschideCosul() {
        System.out.println("📂 Deschidere coș...");
        
        try {
            // Minicart-ul ar putea fi deja deschis după adăugarea produsului
            if (!cartPage.isMinicartVisible()) {
                System.out.println("ℹ️  Minicart nu este vizibil, click pe icon...");
                productPage.clickCartIcon();
                Thread.sleep(1000);
            } else {
                System.out.println("✅ Minicart este deja vizibil");
            }
        } catch (Exception e) {
            System.err.println("⚠️  Eroare la deschiderea coșului: " + e.getMessage());
        }
    }
    
    @Then("produsul este in cosul de cumparaturi")
    public void produsulEsteInCosula() {
        System.out.println("🔍 Verificare produs în coș...");
        
        // NU naviga la o altă pagină, verifică minicart-ul pe pagina curentă
        try {
            // Așteaptă să fie sigur că minicart-ul este vizibil
            Thread.sleep(1000);
            
            boolean isVisible = cartPage.isMinicartVisible();
            System.out.println("ℹ️  Minicart vizibil: " + isVisible);
            
            int itemsCount = cartPage.getCartItemsCount();
            System.out.println("ℹ️  Număr produse în coș: " + itemsCount);
            
            assertTrue(itemsCount > 0, 
                "Coșul ar trebui să aibă cel puțin un produs, dar are: " + itemsCount);
            
            System.out.println("✅ Verificare reușită: " + itemsCount + " produs(e) în coș");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Eroare la așteptare: " + e.getMessage());
        } catch (AssertionError e) {
            System.err.println("❌ Aserțiune eșuată: " + e.getMessage());
            
            // Debug info
            String currentUrl = DriverManager.getDriver().getCurrentUrl();
            System.err.println("🔍 URL curent: " + currentUrl);
            System.err.println("🔍 Page source preview: " + 
                DriverManager.getDriver().getPageSource().substring(0, 
                    Math.min(500, DriverManager.getDriver().getPageSource().length())));
            
            throw e;
        }
    }
}