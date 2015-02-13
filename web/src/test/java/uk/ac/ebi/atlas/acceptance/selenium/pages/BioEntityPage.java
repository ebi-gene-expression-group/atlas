/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BioEntityPage extends HeatmapTableWidgetPage {

    static final String PAGE_LOCATION = "/gxa/";

    private String bioEntityIdentifier;

    private String type;

    @FindBy(id = "searchterm")
    private WebElement searchTerm;

    @FindBy(id = "accordion")
    private WebElement accordion;

    @FindBy(id = "bioEntityCardTable")
    private WebElement table;

    @FindBy(id = "diff-heatmap-table")
    private WebElement diffHeatmapTable;

    @FindBy(id = "infoHeader")
    private WebElement infoPaneHeader;

    @FindBy(id = "baselineProfileHeader")
    private WebElement baselinePaneHeader;

    @FindBy(css = "#baselineProfileHeader span:nth-child(4)")
    private WebElement baselinePaneHeaderSpan;

    @FindBy(id = "diffProfileHeader")
    private WebElement diffProfilePaneHeader;

    @FindBy(css = "#diffProfileHeader span:nth-child(4)")
    private WebElement diffPaneHeaderSpan;

    @FindBy(id = "infoBody")
    private WebElement infoPaneBody;

    @FindBy(id = "baselineProfileBody")
    private WebElement baselineProfilePaneBody;

    private final static String DIFF_PROFILE_BODY_ID = "diffProfileBody";

    @FindBy(id = "widgetBody")
    private WebElement widgetBody;

    @FindBy(css = "h2.strapline")
    private WebElement searchResultHeader;

    @FindBy(css = "[data-icon='u']")
    private WebElement showMoreDataWidget;

    @FindBy(id = "showIndividualGenes")
    private WebElement showIndividualGenes;

    @FindBy(id = "showGeneSetProfiles")
    private WebElement showGeneSetProfiles;

    @FindBy(id = "heatmap-profilesAsGeneSets")
    private WebElement heatmapProfilesAsGeneSets;

    @FindBy(id = "diffresults-display-levels")
    private WebElement diffResultsDisplayLevelsButton;

    @FindBy(id = "goMoreLinks")
    private WebElement showMoreGoLinks;

    @FindBy(id = "goLessLinks")
    private WebElement showLessGoLinks;

    @FindBy(id = "poMoreLinks")
    private WebElement showMorePoLinks;

    @FindBy(id = "poLessLinks")
    private WebElement showLessPoLinks;

    private final By firstColorGradientBar = By.xpath("//div[contains(@class, 'color-gradient')]");

    private final By secondColorGradientBar = By.xpath("//table[@id='diff-heatmap-legend']//tbody//tr[2]//td[2]//div[@class='color-gradient']");

    @FindBy(css = "#global-search-results > li > a")
    private List<WebElement> globalSearchPointers;
    private Iterable<? extends String> diffHeatmapHeaders;

    public BioEntityPage(WebDriver driver) {
        super(driver, null);
    }

    public BioEntityPage(WebDriver driver, String bioEntityIdentifier, String type) {
        super(driver, null);
        this.bioEntityIdentifier = bioEntityIdentifier;
        this.type = type;
    }

    public BioEntityPage(WebDriver driver, String bioEntityIdentifier, String type, String httpParameters) {
        super(driver, null, httpParameters);
        this.bioEntityIdentifier = bioEntityIdentifier;
        this.type = type;
    }

    public void clickDiffResultsDisplayLevelsButton() {
        new FluentWait<>(driver)
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.visibilityOf(diffResultsDisplayLevelsButton));

        diffResultsDisplayLevelsButton.click();
    }


    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + type + "/" + bioEntityIdentifier;
    }

    public String getBioEntityCardTitle() {
        WebElement header = SeleniumUtil.findChildElementWaitingUntilAvailable(driver, accordion, By.className("bioEntityCardHeader"));
        return header.getText();
    }

    public boolean isInfoCardExpanded() {
        return infoPaneBody.isDisplayed();
    }

    public void clickInfoCard(boolean expectToOpen) {
        infoPaneHeader.click();

        if (!expectToOpen) {
            By infoCardBodyId = By.id("infoBody");
            WebDriverWait wait = new WebDriverWait(driver, 10L);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(infoCardBodyId));
        }
    }

    public boolean isBaselinePaneExpanded() {
        SeleniumUtil.findElementByIdWaitingUntilAvailable(driver, "baselineProfileBody");
        return baselineProfilePaneBody.isDisplayed();
    }

    public String getBaselinePaneContents() {
        SeleniumUtil.findElementByIdWaitingUntilAvailable(driver, "baselineProfileBody");
        return baselineProfilePaneBody.getText();
    }

    public boolean isDifferentialPaneExpanded() {
        return SeleniumUtil.elementExists(driver, By.id(DIFF_PROFILE_BODY_ID)) && getDiffProfileBody().isDisplayed();
    }

    public boolean isFirstColorGradientBarPresent() {
        return SeleniumUtil.elementExists(driver, firstColorGradientBar);
    }

    public boolean isSecondColorGradientBarPresent() {
        return SeleniumUtil.elementExists(driver, secondColorGradientBar);
    }

    public String getFirstColorGradientBar() {
        return driver.findElement(firstColorGradientBar).getCssValue("background-image");
    }

    public String getSecondColorGradientBar() {
        return driver.findElement(secondColorGradientBar).getCssValue("background-image");
    }

    private WebElement getDiffProfileBody() {
        return driver.findElement(By.id(DIFF_PROFILE_BODY_ID));
    }

    public void clickDifferentialPane() {
        diffProfilePaneHeader.click();

        By byBioEntityCardClass = By.className("bioEntityCard");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(byBioEntityCardClass));
    }

    public List<String> getContrastColumn() {
        return getColumnValues(this.diffHeatmapTable, getContrastColumnIndex());
    }

    public List<String> getFoldChange() {
        return getColumnValues(this.diffHeatmapTable, getContrastColumnIndex() + 1);
    }

    public List<String> getDiffHeatmapTableGeneColumn() {
        return getColumnValues(this.diffHeatmapTable, getDiffHeatmapTableGeneColumnIndex());
    }

    public List<String> getDiffHeatmapTableOrganismColumn() {
        return getColumnValues(this.diffHeatmapTable, 2);
    }

    public String getDiffHeatmapContrastSummaryTooltipTableHeader(int zeroBasedTooltipTableHeaderIndex) {
        hoverOnFirstContrastDescriptionCell();
        return tooltipHeader(zeroBasedTooltipTableHeaderIndex);
    }

    String tooltipHeader(int zeroBasedTooltipTableHeaderIndex) {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//th[" + (zeroBasedTooltipTableHeaderIndex + 1) + "]");
        return SeleniumUtil.findElementWaitingUntilAvailable(driver, byTooltipClass).getText();
    }

    //This is not working with PhantomJS browser :((
    public String getDiffHeatmapContrastSummaryTooltipTableCell(int zeroBasedTooltipTableRowIndex,
                                                                int zeroBasedTooltipTableColumnIndex) {
        hoverOnFirstContrastDescriptionCell();
        return tooltipCell(zeroBasedTooltipTableRowIndex, zeroBasedTooltipTableColumnIndex);
    }

    private void hoverOnFirstContrastDescriptionCell() {
        WebElement firstContrastDescriptionCell = getCell(diffHeatmapTable, 1, getContrastColumnIndex());
        hoverOnElement(firstContrastDescriptionCell);
    }

    public String getDiffHeatmapContrastSummaryTooltipExperimentDescription() {
        hoverOnFirstContrastDescriptionCell();
        return tooltipContrastExperimentDescription();
    }

    public String getContrastSummaryTooltipContrastDescription() {
        hoverOnFirstContrastDescriptionCell();
        return tooltipContrastDescription();
    }

    public String getLinkInDiffTableRow(int oneBasedExpressionLevelIndex) {
        WebElement firstContrastDescriptionCell = getCell(diffHeatmapTable, oneBasedExpressionLevelIndex, getContrastColumnIndex());
        return firstContrastDescriptionCell.findElements(By.tagName("a")).get(0).getAttribute("href").toString();
    }

    public String getWidgetBody() {
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        wait.until(ExpectedConditions.visibilityOf(widgetBody));
        return widgetBody.getText();
    }

    public int getPropertiesTableSize() {
        return table.findElements(By.tagName("tr")).size();
    }

    public List<String> getPropertiesTableRow(int zeroStartingIndex) {
        List<String> row = Lists.newArrayList();
        WebElement rowElement = table.findElements(By.tagName("tr")).get(zeroStartingIndex);
        for (WebElement dataElement : rowElement.findElements(By.tagName("td"))) {
            row.add(dataElement.getText());
        }
        return row;
    }

    public List<String> getLinksInTableRow(int index) {
        List<String> row = Lists.newArrayList();
        WebElement rowElement = table.findElements(By.tagName("tr")).get(index);
        for (WebElement dataElement : rowElement.findElements(By.tagName("td"))) {
            for (WebElement linkElement : dataElement.findElements(By.tagName("a"))) {
                row.add(linkElement.getAttribute("href"));
            }
        }
        return row;
    }

    public String getSearchResultsHeader() {
        return searchResultHeader.getText();
    }

    public BioEntityPage clickShowMoreDataWidget() {
        showMoreDataWidget.click();
        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#global-search-results > li > a")));
        return this;
    }

    public String getGlobalSearchTerm() {
        return searchTerm.getAttribute("value");
    }

    public String getGlobalSearchAllResultsString() {
        return globalSearchPointers.get(0).getText();
    }

    private static final Pattern GLOBAL_SEARCH_ALL_RESULTS_TOTAL = Pattern.compile("All results \\((.*)\\)");

    public int getGlobalSearchAllResultsTotal() {
        Matcher matcher = GLOBAL_SEARCH_ALL_RESULTS_TOTAL.matcher(getGlobalSearchAllResultsString());
        if (!matcher.matches()) {
            throw new IllegalStateException("Cannot match \"" + GLOBAL_SEARCH_ALL_RESULTS_TOTAL.toString() + "\" in global search widget");
        }
        String total = matcher.group(1).replace(",","");
        return Integer.parseInt(total);
    }

    protected int getContrastColumnIndex() {
        return 1;
    }

    protected int getDiffHeatmapTableGeneColumnIndex() {
        return 1;
    }

    public void clickBaselinePane() {
        baselinePaneHeader.click();

        // wait for the accordion to expand
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getBaselinePaneHeaderResultsMessage() {
        return baselinePaneHeaderSpan.getText();
    }

    public String getDiffPaneHeaderResultsMessage() {
        return diffPaneHeaderSpan.getText();
    }

    public List<String> getDiffHeatmapHeaders() {
        return getTableHeaders(diffHeatmapTable);
    }

    public List<String> getDiffHeatmapRow(int oneBasedRowIndex) {
        return getRowValues(diffHeatmapTable, oneBasedRowIndex);
    }

    public void clickShowMoreGoLinks() {
        showMoreGoLinks.click();
    }

    public void clickShowLessGoLinks() {
        showLessGoLinks.click();
    }

    public void clickShowMorePoLinks() {
        showMorePoLinks.click();
    }

    public void clickShowLessPoLinks() {
        showLessPoLinks.click();
    }

}