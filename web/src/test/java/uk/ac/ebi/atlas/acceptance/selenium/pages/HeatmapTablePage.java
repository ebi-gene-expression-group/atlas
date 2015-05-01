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
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HeatmapTablePage extends TablePage {

    private static final String PAGE_LOCATION = "/gxa/experiments/";

    @FindBy(id = "arrayDesignAccession")
    private WebElement arrayDesignAccession;

    @FindBy(id = "geneCount")
    private WebElement geneCount;

    @FindBy(id = "display-experiment")
    private WebElement displayExperimentLink;

    @FindBy(id = "display-experiment-design")
    private WebElement displayExperimentDesignLink;

    @FindBy(id = "display-analysis-methods")
    private WebElement displayAnalysisLink;

    @FindBy(id = "download-profiles-link")
    private WebElement downloadExpressionProfilesLink;

    @FindBy(id = "download-raw")
    private WebElement downloadRawCountsLink;

    @FindBy(id = "download-analytics")
    private WebElement downloadAnalyticsLink;

    @FindBy(id = "download-normalized")
    private WebElement downloadNormalizedLink;

    @FindBy(id = "download-expressions")
    private WebElement downloadAllExpressions;

    @FindBy(id = "display-qc-report")
    private WebElement qcReportLink;

    @FindBy(id = "clustering-pdf")
    private WebElement clusteringPdfButton;

    @FindBy(id = "download-logFold")
    private WebElement downloadLogFoldLink;

    @FindBy(className = "gxaGradientLevelMin")
    private WebElement gradientLevelsMin;

    @FindBy(className = "gxaGradientLevelMax")
    private WebElement gradientLevelsMax;

    @FindBy(css = "#heatmap-table > tbody > tr > td > span > a")
    private List<WebElement> geneNames;

    @FindBy(xpath = "//label[@for='queryFactorValues']")
    private WebElement queryFactorLabel;

    @FindBy(id = "anatomogram")
    private WebElement anatomogram;

    @FindBy(css = "#gxaExperimentPageHeatmapCountAndLegend > div > div")
    private WebElement diffHeatmapTableLegend;

    @FindBy(xpath = "id('heatmap-table')/thead/tr[2]/th")
    private WebElement geneColumnHeader;

    @FindBy(xpath = "id('heatmap-table')/thead/tr[2]/th[2]")
    private WebElement designElementHeader;

    @FindBy(id = "ensembl-launcher")
    private WebElement ensemblLauncher;

    @FindBy(id = "anatomogram-ensembl-launcher")
    private WebElement anatomogramEnsemblLauncher;

    @FindBy(id = "ensembl-launcher-box-gramene")
    private WebElement grameneLauncher;

    @FindBy(id = "heatmap-message")
    private WebElement heatmapMessage;

    private String experimentAccession;

    public HeatmapTablePage(WebDriver driver, String experimentAccession) {
        this(driver, experimentAccession, null);
    }

    public HeatmapTablePage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, httpParameters);
        this.experimentAccession = experimentAccession;
    }

    protected String getExperimentAccession() {
        return experimentAccession;
    }

    protected int getGeneExpressionStartingRowIndex() {
        try {
            driver.findElement(By.id("arrayDesignAccession")).getAttribute("value");
            return 2; //MicroarrayExperiment, we have the gene name and one column before expression level cells
        } catch (NoSuchElementException e) {
            return 1; //Other experiment types, we have only the gene name before expression level cells
        }
    }

    protected WebElement getHeatmapTable() {
            return SeleniumUtil.findElementByIdWaitingUntilAvailable(driver, "heatmap-table");
    }

    public List<String> getFactorValueHeaders() {
        List<String> queryFactorValues = getTableHeaders(getHeatmapTable());
        //and we need to remove the last header value, because is related to the organism part column
        return queryFactorValues.subList(getGeneExpressionStartingRowIndex(), queryFactorValues.size() - 1);
    }

    public String getHeatmapMessage() {
        return heatmapMessage.getText();
    }

    public List<String> getHeatmapHeaders() {
        return getTableHeaders(this.getHeatmapTable());
    }

    public List<String> getGeneNames() {
        return getFirstColumnValues(getHeatmapTable());
    }

    public List<String> getDesignElementNames() {
        return getSecondColumnValues(getHeatmapTable());
    }

    public String getQueryFactorLabel() {
        return queryFactorLabel.getText();
    }

    public List<String> getHeatmapLegendMinLevels() {
        List<String> result = new ArrayList<>();
        for (WebElement element : diffHeatmapTableLegend.findElements(By.className("gxaGradientLevelMin"))) {
            result.add(element.getText());
        }
        return result;
    }

    public List<String> getHeatmapLegendMaxLevels() {
        List<String> result = new ArrayList<>();
        for (WebElement element : diffHeatmapTableLegend.findElements(By.className("gxaGradientLevelMax"))) {
            result.add(element.getText());
        }
        return result;
    }

    public List<String> getDiffGradientColors() {
        WebElement element = diffHeatmapTableLegend.findElement(By.className("color-gradient"));
        String style = element.getCssValue("background-image");
        return Lists.newArrayList(StringUtils.substringsBetween(style, "rgb(", ")"));
    }

    public String getDisplayExperimentLink() {
        return displayExperimentLink.getAttribute("href");
    }

    public String getDisplayExperimentDesignLink() {
        return displayExperimentDesignLink.getAttribute("href");
    }

    public String getDisplayExperimentAnalysisLink() {
        return displayAnalysisLink.getAttribute("href");
    }

    public String getDownloadExpressionProfilesLink() {
        return downloadExpressionProfilesLink.getAttribute("href");
    }

    public String getDownloadRawCountsLink() {
        return downloadRawCountsLink.getAttribute("href");
    }

    public String getDownloadAnalyticsLink() {
        return downloadAnalyticsLink.getAttribute("href");
    }

    public String getDownloadNormalizedLink() {
        return downloadNormalizedLink.getAttribute("href");
    }

    public String getDownloadAllExpressionsLink() {
        return downloadAllExpressions.getAttribute("href");
    }

    public String getQCReportLink() {
        return qcReportLink.getAttribute("href");
    }

    public String clickClusteringPdfButtonLink() {
        return clusteringPdfButton.getAttribute("href");
    }

    public String getDownloadLogFoldLink() {
        return downloadLogFoldLink.getAttribute("href");
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + experimentAccession;
    }

    public List<String> getFirstGeneProfile() {
        List<String> firstTableRow = getRowValues(getHeatmapTable(), 1);
        return firstTableRow.subList(getGeneExpressionStartingRowIndex(), firstTableRow.size());
    }

    public List<String> getGeneProfile(int oneBasedRowIndex) {
        List<String> rowValues = getRowValues(getHeatmapTable(), oneBasedRowIndex);
        return rowValues.subList(getGeneExpressionStartingRowIndex(), rowValues.size());
    }

    public List<String> getHeatmapRow(int oneBasedRowIndex) {
        return getRowValues(getHeatmapTable(), oneBasedRowIndex);
    }


    public List<String> getLastGeneProfile() {
        List<String> firstTableRow = getLastRowValues(getHeatmapTable());
        return firstTableRow.subList(getGeneExpressionStartingRowIndex(), firstTableRow.size());
    }

    public String getGeneCount() {
        return geneCount.getText();
    }

    public WebElement getDisplayLevelsButton() {
        return SeleniumUtil.findElementByCssWaitingUntilAvailable(driver, "#display-levels");
    }

    public void clickDisplayLevelsButton() {
        WebElement displayLevelsButton = getDisplayLevelsButton();

        new FluentWait<>(driver)
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.visibilityOf(displayLevelsButton));

        displayLevelsButton.click();
    }

    public void waitForHeatmapToBeVisible() {
        SeleniumUtil.waitForElementByIdUntilVisible(driver, "heatmap-react");
    }

    public String getDisplayLevelsButtonValue() {
        return getDisplayLevelsButton().getText();
    }

    public boolean areGradientLevelsHidden() {
        String visibilityMin = gradientLevelsMin.getCssValue("visibility");
        String visibilityMax = gradientLevelsMax.getCssValue("visibility");
        return visibilityMin.equals("hidden") && visibilityMax.equals("hidden");
    }

    public Boolean areExpressionLevelsHidden() {
        WebElement firstExpressionLevelCell = getNonEmptyCellsFromFirstTableRow(getHeatmapTable()).get(getGeneExpressionStartingRowIndex());
        WebElement div = firstExpressionLevelCell.findElement(By.tagName("div"));
        return SeleniumUtil.isVisibilityHidden(div);
    }

    public double getAverageFpkm(int rowIndex) {
        List<String> stringValues = getGeneProfile(rowIndex);
        double averageFpkm = 0D;
        for (String stringValue : stringValues) {
            if (StringUtils.isNotBlank(stringValue)) {
                averageFpkm += Double.parseDouble(stringValue);
            }
        }
        return averageFpkm / stringValues.size();
    }

    public double getMaxExpressionLevel(int rowIndex) {
        List<String> stringValues = getGeneProfile(rowIndex);
        double maxFpkm = 0D;
        for (String stringValue : stringValues) {
            if (StringUtils.isNotBlank(stringValue)) {
                double fpkmValue = Double.parseDouble(stringValue);
                if (fpkmValue > maxFpkm) {
                    maxFpkm = fpkmValue;
                }
            }
        }
        return maxFpkm;
    }

    public String getGeneThatRanksAt(int rowIndex) {
        return getGeneNames().get(rowIndex - 1);
    }


    public WebElement getAnatomogram() {
        return anatomogram;
    }

    protected WebElement getGeneProfileCell(int profileIndex, int expressionIndex) {
        return getCell(getHeatmapTable(), profileIndex + 1, expressionIndex + getGeneExpressionStartingRowIndex() + 1);
    }

    public WebElement getGeneAnchor(int profileIndex) {
        return getGeneAnchor(getHeatmapTable(), profileIndex + 1);
    }

    public boolean hasGeneLink(int profileIndex) {
        return hasGeneAnchor(getHeatmapTable(), profileIndex + 1);
    }

    public String getGeneLink(int profileIndex) {
        WebElement geneAnchor = getGeneAnchor(profileIndex);
        return geneAnchor.getAttribute("href");
    }

    public String getDifferentialExperimentTooltipTableHeader(int zeroBasedProfileIndex, int zeroBasedExpressionLevelIndex, int zeroBasedTooltipTableHeaderIndex, ExperimentType experimentType) {
        WebElement firstGeneProfileCell = getGeneProfileCell(zeroBasedProfileIndex, zeroBasedExpressionLevelIndex);
        hoverOnElement(firstGeneProfileCell);

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//th[" + (zeroBasedTooltipTableHeaderIndex + 1) + "]");
        FluentWait wait = new WebDriverWait(driver, 4L).pollingEvery(1, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public String getDifferentialExperimentTooltipTableCell(int zeroBasedProfileIndex, int zeroBasedExpressionLevelIndex, int zeroBasedTooltipTableCellIndex, ExperimentType experimentType) {
        hoverOnElement(getGeneProfileCell(0, zeroBasedExpressionLevelIndex));

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//td[" + (zeroBasedTooltipTableCellIndex + 1) + "]");
        FluentWait wait = new WebDriverWait(driver, 4L).pollingEvery(1, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public String getGenePropertyTooltipContent(int zeroBasedProfileIndex) {
        WebElement geneProfileHeaderCell = getGeneAnchor(zeroBasedProfileIndex);
        hoverOnElement(geneProfileHeaderCell);

        By byTooltipClass = By.className("gxaGeneNameTooltip");
        WebDriverWait wait = new WebDriverWait(driver, 8L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public String getFactorTooltipHeader(int zeroBasedTooltipTableHeaderIndex) {
        hoverOnHeaderColumn(1);

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//th[" + (zeroBasedTooltipTableHeaderIndex + 1) + "]");
        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public String getFactorTooltipContent(int oneBasedHeaderColumn,  int zeroBasedTooltipTableRowIndex,
                                                         int zeroBasedTooltipTableColumnIndex) {
        hoverOnHeaderColumn(oneBasedHeaderColumn);

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//tr[" + (zeroBasedTooltipTableRowIndex + 1)
                + "]//td[" + (zeroBasedTooltipTableColumnIndex + 1) + "]");
        FluentWait wait = new WebDriverWait(driver, 6L)
                .pollingEvery(50, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();

    }

    public String getContrastTooltipTableHeader(int zeroBasedTooltipTableHeaderIndex) {
        hoverOnHeaderColumn(1);
        return tooltipHeader(zeroBasedTooltipTableHeaderIndex);
    }

    String tooltipHeader(int zeroBasedTooltipTableHeaderIndex) {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//th[" + (zeroBasedTooltipTableHeaderIndex + 1) + "]");
        return SeleniumUtil.findElementWaitingUntilAvailable(driver, byTooltipClass).getText();
    }

    //This is not working with PhantomJS browser :((
    public String getContrastTooltipContent(int oneBasedHeaderColumn, int zeroBasedTooltipTableRowIndex,
                                                                int zeroBasedTooltipTableColumnIndex) {
        hoverOnHeaderColumn(oneBasedHeaderColumn);
        return tooltipCell(zeroBasedTooltipTableRowIndex, zeroBasedTooltipTableColumnIndex);
    }

    String tooltipCell(int zeroBasedTooltipTableRowIndex, int zeroBasedTooltipTableColumnIndex) {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//tr[" + (zeroBasedTooltipTableRowIndex + 1)
                + "]//td[" + (zeroBasedTooltipTableColumnIndex + 1) + "]");
        return SeleniumUtil.findElementWaitingUntilAvailable(driver, byTooltipClass).getText();
    }

    public String getContrastTooltipExperimentDescription() {
        hoverOnHeaderColumn(1);
        return tooltipContrastExperimentDescription();
    }

    String tooltipContrastExperimentDescription() {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//div[@id='contrastExperimentDescription']");
        return SeleniumUtil.findElementWaitingUntilAvailable(driver, byTooltipClass).getText();
    }

    public String getContrastTooltipContrastDescription() {
        hoverOnHeaderColumn(1);
        return tooltipContrastDescription();
    }

    String tooltipContrastDescription() {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//div[@id='contrastDescription']");
        return SeleniumUtil.findElementWaitingUntilAvailable(driver, byTooltipClass).getText();
    }

    private void hoverOnHeaderColumn(int oneBasedHeaderIndex) {
        List<WebElement> tableHeaders = getHeatmapTable().findElements(By.xpath(TABLE_HEADERS_XPATH));
        WebElement headerCell = tableHeaders.get(oneBasedHeaderIndex);
        hoverOnElement(headerCell);
    }

    public List<String> getGenePropertyTooltipHighlightedTerms(int zeroBasedProfileIndex) {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//span[@class='gxaHighlight']");
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        List<WebElement> highlightedTermElements = driver.findElements(byTooltipClass);
        List<String> highlightedTerms = Lists.newArrayList();
        for (WebElement highlightedTermElement : highlightedTermElements) {
            highlightedTerms.add(highlightedTermElement.getText());
        }
        return highlightedTerms;
    }

    protected void hoverOnElement(WebElement webElement) {
        Action builder;
        Actions hover = new Actions(driver);
        hover.moveToElement(webElement);
        builder = hover.build();
        builder.perform();
    }

    public String getGeneColumnHeader() {
        return geneColumnHeader.getText();
    }

    public String getDesignElementHeader() {
        return designElementHeader.getText();
    }

    public BioEntityPage clickGeneName(int zeroBasedGeneNameIndex) {
        geneNames.get(zeroBasedGeneNameIndex).click();


        WebDriverWait wait = new WebDriverWait(driver, 15L);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("accordion")));

        return new BioEntityPage(driver);
    }

    public boolean hasAnatomogram() {
        return !driver.findElements(By.cssSelector("#anatomogram svg")).isEmpty();
    }

    public boolean hasEnsemblLauncher() {
        return (hasAnatomogram() && SeleniumUtil.findChildElements(anatomogramEnsemblLauncher).size() > 0) || SeleniumUtil.findChildElements(ensemblLauncher).size() > 0;
    }

    public boolean hasGrameneLauncher() {
        return hasEnsemblLauncher() && SeleniumUtil.findChildElements(grameneLauncher).size() > 0;
    }

}
