
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeatmapTableWithMaPlotButtonsPage extends HeatmapTablePage {

    @FindBy(className = "ma-button")
    private List<WebElement> maPlotButtons;

    public HeatmapTableWithMaPlotButtonsPage(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithMaPlotButtonsPage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public HeatmapTableWithMaPlotButtonsPage clickMaPlotButton(int zeroBasedButtonIndex) {
        maPlotButtons.get(zeroBasedButtonIndex).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return driver.findElement(By.className("fancybox-image")) != null;
            }
        });
        return this;
    }

    public String getMaPlotImageAnchor(){

        WebElement maPlotImageElement = driver.findElement(By.className("fancybox-image"));
        return maPlotImageElement.getAttribute("src");
    }
}