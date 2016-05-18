
package uk.ac.ebi.atlas.acceptance.selenium.fixture.internal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.DriverFactory;

import java.net.MalformedURLException;
import java.net.URL;


/*
In order to use this fixture you must have PhantomJS installed on your machine and you must start it:
as a webrunner server with the following command line:
phantomjs --webdriver=4444
 */
public class PhantomJSDriverFactory implements DriverFactory {

    private static final String SELENIUM_SERVER_URL = "http://127.0.0.1:4444/wd/hub";

    protected WebDriver driver;


    @Override
    public WebDriver create() {
        return initializeDriver();
    }

    private WebDriver initializeDriver() {

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setJavascriptEnabled(true);
            capabilities.setBrowserName("firefox");

            return new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capabilities);
        } catch (MalformedURLException e) {

            e.printStackTrace();
            throw new IllegalStateException(e);

        }

    }


}
