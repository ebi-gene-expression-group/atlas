package uk.ac.ebi.atlas.acceptance.selenium.utils;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public abstract class SeleniumFixture {

    private static final String SELENIUM_SERVER_URL = "http://ma-selenium:4444/wd/hub";

    //Uncomment this if you want to use local firefox browser
    //protected FirefoxDriver driver;

    //comment this if you want to use local firefox browser
    protected WebDriver driver;


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

        //uncomment this if you want to use local firefox driver
        //this.driver = new FirefoxDriver();


        try {

            //comment this if you want to use local firefox browser
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setJavascriptEnabled(true);
            capabilities.setBrowserName("firefox");
            //

            this.driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capabilities);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

    }

    protected abstract void getStartingPage();

}
