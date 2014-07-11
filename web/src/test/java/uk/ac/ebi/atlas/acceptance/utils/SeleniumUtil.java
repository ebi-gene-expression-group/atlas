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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {

    public static final int TIMEOUT_DURATION = 15;

    /*
                Find an element by ID, but wait until the element is available first, ignoringNoSuchElementException errors.
                */
    public static WebElement findElementByIdWaitingUntilAvailable(final WebDriver driver, final String id) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.id(id));
            }
        });
    }

    public static void waitForElementByIdUntilVisible(final WebDriver driver, final String id) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public static WebElement findElementByCssWaitingUntilAvailable(final WebDriver driver, final String css) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.cssSelector(css));
            }
        });
    }

    public static WebElement findElementWaitingUntilAvailable(final WebDriver driver, final By by) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(by);
            }
        });
    }

    public static WebElement findChildElementWaitingUntilAvailable(final WebDriver driver, final WebElement parent, final By by) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
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

    public static void waitForNumberOfWindowsToBe(final WebDriver driver, final int numberOfWindows) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS);

        wait.until(numberOfWindowsToBe(numberOfWindows));
    }

    public static void waitForPageTitle(final WebDriver driver) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS);

        wait.until(new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver input) {
                return StringUtils.isNotBlank(driver.getTitle());
            }
        });
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

    public static void switchToOpenedWindow(final WebDriver driver) {
        waitForNumberOfWindowsToBe(driver, 2);

        String currentWindowHandle = driver.getWindowHandle();
        String firstOtherWindowHandle = SeleniumUtil.firstOtherWindowHandle(driver, currentWindowHandle);
        driver.switchTo().window(firstOtherWindowHandle);
    }

    public static String firstOtherWindowHandle(final WebDriver driver, String currentWindowHandle) {

        Set<String> allWindowHandles = driver.getWindowHandles();

        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(currentWindowHandle)) {
                return windowHandle;
            }
        }

        throw new RuntimeException("No window handle other than " + currentWindowHandle);

    }

}
