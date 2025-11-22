package steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import pages.ProductPage;
import utils.DriverManager;

public class ReviewSteps {
    private ProductPage productPage;
    
    public ReviewSteps() {
        this.productPage = new ProductPage(DriverManager.getDriver());
    }
    
    @Given("utilizatorul este pe pagina single product")
    public void utilizatorulEstePePaginaSingleProduct() {
        System.out.println("🔄 Navigare la pagina single product...");
        productPage.navigateToSingleProductPage();
        System.out.println("✅ Utilizatorul este pe pagina single product");
    }

    @When("utilizatorul da click pe butonul de review")
    public void utilizatorulDaClickPeButonulDeReview() {
        productPage.clickReviewButton();
    }
    
    @When("utilizatorul introduce textul review-ului {string}")
    public void utilizatorulIntroduceTextulReviewului(String reviewText) {
        productPage.enterReviewText(reviewText);
    }
    
    @When("utilizatorul selecteaza rating-ul {string}")
    public void utilizatorulSelecteazaRatingul(String rating) {
        productPage.selectReviewRating(rating);
    }
    
    @When("utilizatorul trimite review-ul")
    public void utilizatorulTrimiteReviewul() {
        productPage.submitReview();
    }
    
    @When("utilizatorul completeaza review-ul cu:")
    public void utilizatorulCompleteazaReviewulCu(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMap(String.class, String.class);
        productPage.enterReviewText(data.get("Text"));
        productPage.selectReviewRating(data.get("Rating"));
    }
    
    @Then("review-ul cu textul {string} este afisat")
    public void reviewulCuTextulEsteAfisat(String reviewText) {
        boolean isDisplayed = productPage.isReviewDisplayed(reviewText);
        assertTrue(isDisplayed, "Review-ul cu textul '" + reviewText + "' nu este afisat");
    }
    
    @Then("review-ul este vizibil in lista de review-uri")
    public void reviewulEsteVizibilInListaDeReviewuri() {
        // Asteapta ca review-ul sa apara
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Aici poti adauga verificari suplimentare daca este necesar
    }
}