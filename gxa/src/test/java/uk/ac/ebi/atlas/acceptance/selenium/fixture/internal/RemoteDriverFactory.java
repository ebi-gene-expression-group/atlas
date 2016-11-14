
package uk.ac.ebi.atlas.acceptance.selenium.fixture.internal;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class RemoteDriverFactory implements DriverFactory {

    private static final String SELENIUM_SERVER_URL = "http://ma-selenium:4444/wd/hub";
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
