package uk.ac.ebi.atlas.acceptance.utils;

import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {

    public static final int TIMEOUT_DURATION = 20;

    public static boolean elementExists(WebDriver driver, By by) {
        return !driver.findElements(by).isEmpty();
    }

    /*
    Find an element by ID, but wait until the el0ement is available first, ignoringNoSuchElementException errors.
    */
    public static WebElement findElementByIdWaitingUntilAvailable(final WebDriver driver, final String id) {
        return findElementWaitingUntilAvailable(driver, By.id(id));
    }

    public static WebElement findElementWaitingUntilAvailable(final WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }
        });
    }

    public static void waitForElementByIdUntilVisible(final WebDriver driver, final String id) {
        waitUntilElementVisible(driver, By.id(id));
    }

    public static void waitUntilElementVisible(final WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement findElementByCssWaitingUntilAvailable(final WebDriver driver, final String css) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.cssSelector(css));
            }
        });
    }

    public static WebElement findChildElementWaitingUntilAvailable(final WebDriver driver, final WebElement parent, final By by) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return parent.findElement(by);
            }
        });
    }

    public static List<WebElement> findChildElements(WebElement element) {
        return element.findElements(By.xpath("./*"));
    }

    public static ExpectedCondition<Boolean> numberOfWindowsToBe(final int numberOfWindows) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                driver.getWindowHandles();
                return driver.getWindowHandles().size() == numberOfWindows;
            }
        };

    }

    public static Boolean isVisibilityHidden(WebElement element) {
        return element.getAttribute("style").contains("visibility: hidden");
    }
}
