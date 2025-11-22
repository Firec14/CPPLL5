package steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import pages.ProductPage;
import utils.DriverManager;
import java.util.List;

public class SortingSteps {
    private ProductPage productPage;
    private List<String> productTitlesBefore;
    private List<Double> productPricesBefore;
    
    public SortingSteps() {
        this.productPage = new ProductPage(DriverManager.getDriver());
    }
    
    @Given("utilizatorul este pe pagina de produse")
    public void utilizatorulEstePePaginaDeProduse() {
        productPage.navigateToProductPage();
    }
    
    @When("utilizatorul selecteaza sortarea dupa {string}")
    public void utilizatorulSelecteazaSortareaDupa(String sortOption) {
        productPricesBefore = productPage.getProductPrices();
        productTitlesBefore = productPage.getProductTitles();
        productPage.selectSortOption(sortOption);
    }
    
    @Then("produsele sunt sortate crescator dupa pret")
    public void produseleSuntSortateCrescatorDupaPret() {
        List<Double> pricesAfter = productPage.getProductPrices();
        assertFalse(pricesAfter.isEmpty(), "Lista de preturi nu trebuie sa fie goala");
        
        for (int i = 0; i < pricesAfter.size() - 1; i++) {
            assertTrue(
                pricesAfter.get(i) <= pricesAfter.get(i + 1),
                "Pretul " + pricesAfter.get(i) + " trebuie sa fie mai mic sau egal cu " + pricesAfter.get(i + 1)
            );
        }
    }
    
    @Then("produsele sunt sortate descrescator dupa pret")
    public void produseleSuntSortateDescrescatorDupaPret() {
        List<Double> pricesAfter = productPage.getProductPrices();
        assertFalse(pricesAfter.isEmpty(), "Lista de preturi nu trebuie sa fie goala");
        
        for (int i = 0; i < pricesAfter.size() - 1; i++) {
            assertTrue(
                pricesAfter.get(i) >= pricesAfter.get(i + 1),
                "Pretul " + pricesAfter.get(i) + " trebuie sa fie mai mare sau egal cu " + pricesAfter.get(i + 1)
            );
        }
    }
    
    @Then("produsele sunt sortate alfabetic dupa nume")
    public void produseleSuntSortateAlfabeticDupaNume() {
        List<String> titlesAfter = productPage.getProductTitles();
        assertFalse(titlesAfter.isEmpty(), "Lista de titluri nu trebuie sa fie goala");
        
        for (int i = 0; i < titlesAfter.size() - 1; i++) {
            assertTrue(
                titlesAfter.get(i).compareToIgnoreCase(titlesAfter.get(i + 1)) <= 0,
                "Titlul " + titlesAfter.get(i) + " trebuie sa fie inainte de " + titlesAfter.get(i + 1) + " alfabetic"
            );
        }
    }
    
    @Then("ordinea produselor s-a schimbat")
    public void ordineaProduselorSaSchimbat() {
        List<String> titlesAfter = productPage.getProductTitles();
        assertNotEquals(productTitlesBefore, titlesAfter, 
            "Ordinea produselor trebuie sa se schimbe dupa sortare");
    }
}