
package uk.ac.ebi.atlas.acceptance.selenium.fixture;

public class SeleniumFixture extends SinglePageSeleniumFixture {

    @Override
    protected void getStartingPage() {
        // do nothing... test classes extending this fixture still inherit an initialized WebDriver instance
        // but are responsible of instantiating and using Page classes
    }
}
