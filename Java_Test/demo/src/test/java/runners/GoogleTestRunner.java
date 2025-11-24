package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/GoogleSearch.feature",
    glue = {
        "steps",
        "hooks"
    },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/google-search-report.html",
        "json:target/cucumber-reports/google-search.json",
        "junit:target/cucumber-reports/google-search.xml"
    },
    monochrome = true,
    tags = ""  // Remove the @google tag filter for now
)
public class GoogleTestRunner {
}