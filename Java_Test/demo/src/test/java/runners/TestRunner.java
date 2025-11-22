package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"steps", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = true,
    dryRun = false,
    tags = "not @skip"
)
public class TestRunner {
    // Acest runner va executa toate scenariile Cucumber
    // Pentru a rula teste specifice, folositi tags:
    // tags = "@smoke" - ruleaza doar teste cu tag-ul @smoke
    // tags = "@regression" - ruleaza doar teste cu tag-ul @regression
    // tags = "@sorting or @cart" - ruleaza teste cu tag-ul @sorting SAU @cart
    // tags = "@login and @smoke" - ruleaza teste cu AMBELE tag-uri
}