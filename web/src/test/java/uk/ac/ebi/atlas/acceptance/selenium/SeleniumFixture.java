package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class SeleniumFixture {

    protected FirefoxDriver firefoxDriver;

    @Before
    public void bootstrapTest() {
        initializeFirefoxDriver();
        getStartingPage();
    }

    @After
    public void closeDriver() {
        firefoxDriver.quit();
    }

    private void initializeFirefoxDriver() {
        this.firefoxDriver = new FirefoxDriver();
    }

    protected abstract void getStartingPage();

}
