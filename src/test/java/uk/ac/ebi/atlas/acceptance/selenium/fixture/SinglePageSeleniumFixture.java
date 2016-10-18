
package uk.ac.ebi.atlas.acceptance.selenium.fixture;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.FirefoxDriverFactory;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.PhantomJSDriverFactory;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.internal.RemoteDriverFactory;


public abstract class SinglePageSeleniumFixture {

    protected WebDriver driver;

    @Before
    public void initDriver() {
        // change the factory to FirefoxDriverFactory or PhantomJSDriverFactory
        // if you want to use a local browser
        //driver = new FirefoxDriverFactory().create();
        //driver = new PhantomJSDriverFactory().create();
        driver = new RemoteDriverFactory().create();
        getStartingPage();
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    protected abstract void getStartingPage();

}
