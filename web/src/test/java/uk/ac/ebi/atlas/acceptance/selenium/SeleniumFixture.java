package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.concurrent.TimeUnit;

public abstract class SeleniumFixture {

    protected FirefoxDriver firefoxDriver;

    @Before
    public void bootstrapTest() {
        initializeFirefoxDriver();
        getStartingPage();
    }

    @After
    public void closeDriver() {
        System.out.println("Closing = " + firefoxDriver);
        firefoxDriver.quit();
    }

    private void initializeFirefoxDriver() {
        this.firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    protected abstract LoadableComponent getStartingPage();

}
