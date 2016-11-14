
package uk.ac.ebi.atlas.acceptance.selenium.fixture;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.RemoteDriverFactory;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;

//creates only a single driver for the whole test
public abstract class SingleDriverSeleniumFixture {

    static WebDriver driver;

    public static WebDriver create() {
        // change the factory to FirefoxDriverFactory or PhantomJSDriverFactory
        // if you want to use a local browser
        //driver = new FirefoxDriverFactory().create();
        //driver = new PhantomJSDriverFactory().create();
        driver = new RemoteDriverFactory().create();
        return driver;
    }

    @AfterClass
    public static void quitDriver() {
        driver.quit();
    }

}
