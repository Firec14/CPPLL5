# Copilot Instructions for Java Test Automation Framework

## Project Overview
BDD-driven Selenium test automation framework using Cucumber 7.x with Page Object Model (POM) pattern. Tests run against a single-page application at `https://loving-hermann-e2094b.netlify.app`. Features written in **Romanian Gherkin** with glue code in Java 17.

## Architecture & Key Patterns

### Layer Structure
1. **Feature Files** (`src/test/resources/features/*.feature`)
   - Written in Romanian: `Functionalitate`, `Scenariu`, `Dat`, `Cand`, `Atunci`
   - Single application domain: cart, login, registration, sorting, review, contact, Google search
   
2. **Step Definitions** (`src/test/java/steps/*.java`)
   - One steps file per feature domain (e.g., `LoginSteps.java`, `CartSteps.java`)
   - Constructor injects page objects: `this.loginPage = new LoginPage(DriverManager.getDriver())`
   - Use `@Given`, `@When`, `@Then` annotations with Romanian step names

3. **Page Objects** (`src/test/java/pages/*.java`)
   - Encapsulate locators and interactions for each domain
   - Constructor receives `WebDriver` and initializes `WebDriverWait(driver, Duration.ofSeconds(10))`
   - Methods handle explicit waits using `ExpectedConditions` (e.g., `elementToBeClickable`, `visibilityOfElementLocated`)
   - Example from `LoginPage`: Uses `By` locators with XPath/CSS selectors for modal elements

4. **Hooks** (`src/test/java/hooks/Hooks.java`)
   - `@Before`: Initializes driver via `DriverManager.getDriver()`, navigates to homepage, validates URL
   - `@After`: Captures screenshots on scenario failure

5. **Utils** (`src/test/java/utils/DriverManager.java`)
   - ThreadLocal WebDriver management via `DriverManager.getDriver()`
   - Browser configuration: `-Dbrowser=chrome|firefox|edge|safari`, `-Dheadless=true|false`
   - Uses WebDriverManager (v5.6.4) for automatic driver binary management

### Test Runners
- **TestRunner** (`runners/TestRunner.java`): All tests except @skip
  - Plugins: pretty, HTML, JSON, JUnit XML reports to `target/cucumber-reports/`
- **GoogleTestRunner** (`runners/GoogleTestRunner.java`): Google search tests

## Development Workflows

### Run Tests
```bash
# All tests
mvn clean test

# Specific tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@login and @smoke"

# Browser & headless options
mvn test -Dbrowser=firefox -Dheadless=true
```

### Adding New Test
1. Create `.feature` file in `src/test/resources/features/` with Romanian steps
2. Create corresponding `*Steps.java` and `*Page.java` in glue paths (`steps`, `hooks`)
3. Use ThreadLocal driver from `DriverManager.getDriver()` in page objects
4. Register steps and hooks—Cucumber auto-discovers via `glue` paths

### Expected Waits & Timeouts
- WebDriverWait default: 10 seconds (`Duration.ofSeconds(10)`)
- Thread.sleep() for modal animations (500ms-2000ms in hooks/pages)
- Modal interactions often require JavaScript fallback: `((JavascriptExecutor) driver).executeScript("$('#myModal').modal('show');")`

## Project-Specific Conventions

### File Organization
- One page per domain (not one per unique page URL—SPA with modals)
- Steps named after Gherkin: `LoginSteps` → `Login.feature`
- No cross-feature step sharing (each domain is isolated)

### Locator Strategy
- Prefer XPath with predicates: `//input[@name='Email']`
- Use data attributes when available: `@data-toggle`, `@data-target`
- CSS selectors for pseudo-selectors: `#myModal a.instagram`
- Modal states tracked via `#myModal` ID and visibility checks

### Assertions
- Use JUnit 5 (`org.junit.jupiter.api.Assertions`) for assertions
- Example: `assertTrue(driver.getTitle().toLowerCase().contains(expectedText.toLowerCase()))`
- Assertion messages should be descriptive for test reports

### Dependencies
- **Cucumber**: 7.15.0 (Java, JUnit Platform Engine, Picocontainer for DI)
- **Selenium**: 4.20.0
- **WebDriverManager**: 5.6.4
- **AssertJ**: 3.25.3
- **JUnit**: 5.10.2 + 4.13.2 (backward compat)

## Common Patterns in Codebase

### Modal Interactions
```java
// LoginPage pattern
WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(loginTrigger));
trigger.click();
wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
Thread.sleep(500); // Animation
```

### Cross-Browser Support
Automatic via system properties; DriverManager handles Chrome, Firefox, Edge, Safari.

### Error Handling
- Page objects throw `RuntimeException` on critical failures (e.g., navigation)
- Screenshots captured automatically on scenario failure via `@After` hook
- System.out debugging: `System.out.println("✅ Status message")`

## File References
- **Build**: `pom.xml` (Maven 3, JDK 17)
- **Config**: `src/test/java/utils/DriverManager.java` (driver lifecycle)
- **Templates**: `src/test/java/pages/LoginPage.java` (POM example), `LoginSteps.java` (steps template)
- **Report config**: `runners/TestRunner.java` (plugin paths)
