package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.concurrent.TimeUnit;

public abstract class SeleniumFixture {

    private FirefoxDriver initializeFirefoxDriver() {
        final FirefoxDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (firefoxDriver != null) {
                    firefoxDriver.quit();
                }
            }
        });
        return firefoxDriver;
    }

    @Before
    public void bootstrapTest(){
        FirefoxDriver driver = initializeFirefoxDriver();
        getStartingPage(driver);
    }

    protected abstract LoadableComponent getStartingPage(FirefoxDriver firefoxDriver);

}
