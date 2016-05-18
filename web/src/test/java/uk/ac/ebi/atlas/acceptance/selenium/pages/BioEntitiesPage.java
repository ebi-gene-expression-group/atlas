
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BioEntitiesPage extends BioEntityPage {

    private String url;

    @FindBy(xpath = "//*[@id=\"diffProfileBody\"]/div[1]/span[1]")
    private WebElement resultCountLine;

    public BioEntitiesPage(WebDriver driver) {
        super(driver);
    }

    public BioEntitiesPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    public static BioEntitiesPage search(WebDriver driver, String queryParams) {
        return new BioEntitiesPage(driver, "query?" + queryParams);
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + url;
    }

    @Override
    protected int getContrastColumnIndex() {
        return 3;
    }

    public List<BaselineBioEntitiesSearchResult> getAllBaselineResultsWithoutSpecies() {
        return getBaselineResults(false, false);
    }

    public List<BaselineBioEntitiesSearchResult> getAllBaselineResults() {
        return getBaselineResults(true, false);
    }

    public List<BaselineBioEntitiesSearchResult> getVisibleBaselineResultsWithoutSpecies() {
        return getBaselineResults(false, true);
    }

    public List<BaselineBioEntitiesSearchResult> getVisibleBaselineResults() {
        return getBaselineResults(true, true);
    }

    public List<BaselineBioEntitiesSearchResult> getBaselineResults(boolean hasSpecies, boolean onlyVisible) {
        List<BaselineBioEntitiesSearchResult> baselineCounts = Lists.newArrayList();

        By byBaselineCountsTableId = By.id("baselineCountsTable");
        Wait<WebDriver> wait = new WebDriverWait(driver, 25L).pollingEvery(20, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byBaselineCountsTableId));

        List<WebElement> linkElements = driver.findElements(By.cssSelector("#baselineCountsTable a.gxaBioEntityCardLink"));

        for (WebElement linkElement : linkElements) {
            if (onlyVisible && !linkElement.isDisplayed()) {
                continue;
            }
            baselineCounts.add(buildBaselineEntityCount(linkElement, null, hasSpecies));
        }

        return baselineCounts;
    }

    public List<WebElement> getBaselineCountElements() {
        return driver.findElements(By.className("gxaBioEntityCardLink"));
    }

    private BaselineBioEntitiesSearchResult buildBaselineEntityCount(WebElement linkElement, WebElement countElement) {
        return buildBaselineEntityCount(linkElement, countElement, true);
    }

    private BaselineBioEntitiesSearchResult buildBaselineEntityCount(WebElement linkElement, WebElement countElement, boolean hasSpecies) {

        String linkText = linkElement.getText();
        String linkHref = linkElement.getAttribute("href");

        String species = hasSpecies ? StringUtils.substringBefore(linkText, " - ").trim() : null;
        String experimentName = hasSpecies ? StringUtils.substringAfter(linkText, " - ").trim() : linkText.trim();

        String experimentAccession = StringUtils.substringAfterLast(linkHref, "/");
        experimentAccession = StringUtils.substringBeforeLast(experimentAccession, "?");

        int baselineCount = (countElement == null) ? -1 : Integer.parseInt(StringUtils.substringBetween(countElement.getText(), "(", ")"));

        return new BaselineBioEntitiesSearchResult(experimentName, species, experimentAccession, baselineCount, linkHref);
    }

    public String diffExpressionResultCount() {
        return resultCountLine.getText();
    }
}

