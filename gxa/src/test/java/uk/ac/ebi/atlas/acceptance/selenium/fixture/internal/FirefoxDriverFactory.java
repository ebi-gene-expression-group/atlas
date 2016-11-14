
package uk.ac.ebi.atlas.acceptance.selenium.fixture.internal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.DriverFactory;

public class FirefoxDriverFactory implements DriverFactory {

    protected FirefoxDriver driver;

    @Override
    public WebDriver create() {
        return initializeDriver();
    }

    private WebDriver initializeDriver() {

        return new FirefoxDriver();

    }

}
