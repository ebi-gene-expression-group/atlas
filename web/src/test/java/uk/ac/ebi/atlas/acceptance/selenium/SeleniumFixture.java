package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;


public abstract class SeleniumFixture {

    //private static final String SELENIUM_SERVER_URL = "http://localhost:4444/wd/hub";

    protected FirefoxDriver driver;

    //protected WebDriver driver;


    @Before
    public void bootstrapTest() {
        initializeFirefoxDriver();
        getStartingPage();
    }

    @After
    public void closeDriver() {
        driver.quit();
    }

    private void initializeFirefoxDriver() {

        this.driver = new FirefoxDriver();

        /*
        try {
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setJavascriptEnabled(true);
            capabilities.setBrowserName("firefox");
            this.driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capabilities);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }
        */
    }

    protected abstract void getStartingPage();

}
