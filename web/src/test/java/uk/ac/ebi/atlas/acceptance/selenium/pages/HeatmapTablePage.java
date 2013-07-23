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
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HeatmapTablePage extends TablePage {

    private static final String PAGE_LOCATION = "/gxa/experiments/";

    @FindBy(id = "arrayDesignAccession")
    private WebElement arrayDesignAccession;

    @FindBy(id = "heatmap-table")
    private WebElement heatmapTable;

    @FindBy(id = "geneCount")
    private WebElement geneCount;

    @FindBy(id = "download-profiles-link")
    private WebElement downloadExpressionProfilesLink;

    @FindBy(id = "download-raw")
    private WebElement downloadRawCountsLink;

    @FindBy(id = "download-analytics")
    private WebElement downloadAnalyticsLink;

    @FindBy(id = "download-normalized")
    private WebElement downloadNormalizedLink;

    @FindBy(id = "download-logFold")
    private WebElement downloadLogFoldLink;

    @FindBy(id = "display-levels")
    private WebElement displayLevelsButton;

    @FindBy(className = "gradient-level-min")
    private WebElement gradientLevelsMin;

    @FindBy(className = "gradient-level-max")
    private WebElement gradientLevelsMax;

    @FindBy(className = "genename")
    private List<WebElement> geneNames;

    @FindBy(xpath = "//label[@for='queryFactorValues']")
    private WebElement queryFactorLabel;

    @FindBy(id = "anatomogram")
    private WebElement anatomogram;

    @FindBy(id = "diff-heatmap-legend")
    private WebElement diffHeatmapTableLegend;

    @FindBy(xpath = "//thead/tr/td[1]")
    private WebElement geneColumnHeader;

    @FindBy(xpath = "//thead/tr/td[2]")
    private WebElement designElementHeader;

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
            return 2; //MicroarrayExperiment, we have two columns before expression level cells
        } catch (NoSuchElementException e) {
            return 1; //Other experiment types, we have one column before expression level cells
        }
    }

    protected WebElement getHeatmapTable() {
        return heatmapTable;
    }

    protected void setHeatmapTable(WebElement heatmapTable) {
        this.heatmapTable = heatmapTable;
    }


    public List<String> getFactorValueHeaders() {
        List<String> queryFactorValues = getTableHeaders(getHeatmapTable());
        //and we need to remove the last header value, because is related to the organism part column
        return queryFactorValues.subList(getGeneExpressionStartingRowIndex(), queryFactorValues.size());
    }

    public List<String> getSelectedProfiles() {
        return getFirstColumnValues(getHeatmapTable());
    }

    public String getQueryFactorLabel() {
        return queryFactorLabel.getText();
    }

    public List<String> getDiffGradientMinLabels() {
        List<String> result = new ArrayList<>();
        for (WebElement element : diffHeatmapTableLegend.findElements(By.className("gradient-level-min"))) {
            result.add(element.getText());
        }
        return result;
    }

    public List<String> getDiffGradientMaxLabels() {
        List<String> result = new ArrayList<>();
        for (WebElement element : diffHeatmapTableLegend.findElements(By.className("gradient-level-max"))) {
            result.add(element.getText());
        }
        return result;
    }

    public List<String> getDiffGradientColors() {
        List<String> result = new ArrayList<>();
        WebElement element = diffHeatmapTableLegend.findElement(By.className("color-gradient"));
        String style = element.getCssValue("background-image");
        result.add(StringUtils.substringBetween(style,"from(rgb(",")"));
        result.add(StringUtils.substringBetween(style,"to(rgb(",")"));
        return result;
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

    public List<String> getGeneProfile(int zeroBasedRowIndex) {
        List<String> rowValues = getRowValues(getHeatmapTable(), zeroBasedRowIndex);
        return rowValues.subList(getGeneExpressionStartingRowIndex(), rowValues.size());
    }


    public List<String> getLastGeneProfile() {
        List<String> firstTableRow = getLastRowValues(getHeatmapTable());
        return firstTableRow.subList(getGeneExpressionStartingRowIndex(), firstTableRow.size());
    }

    public String getGeneCount() {
        return geneCount.getText();
    }

    public void clickDisplayLevelsButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("display-levels")));

        displayLevelsButton.click();
    }

    public String getDisplayLevelsButtonValue() {
        return displayLevelsButton.getText();
    }

    public boolean areGradientLevelsHidden() {
        String style = gradientLevelsMin.getAttribute("style");
        String style2 = gradientLevelsMax.getAttribute("style");
        return style.contains("display") && style.contains("none") && style2.contains("display") && style2.contains("none");
    }

    public Boolean areExpressionLevelsHidden() {
        //we get the cell at index 1 because at index 0 we have the gene name
        WebElement firstExpressionLevelCell = getNonEmptyCellsFromFirstTableRow(getHeatmapTable()).get(getGeneExpressionStartingRowIndex());
        WebElement div = firstExpressionLevelCell.findElement(By.tagName("div"));
        return div.getAttribute("class").contains("hide_cell");
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
        return getSelectedProfiles().get(rowIndex - 1);
    }


    public WebElement getAnatomogram() {
        return anatomogram;
    }

    protected WebElement getGeneProfileCell(int profileIndex, int expressionIndex) {
        return getCell(getHeatmapTable(), profileIndex + 1, expressionIndex + getGeneExpressionStartingRowIndex() + 1);
    }

    protected WebElement getGeneAnchor(int profileIndex) {
        return getGeneAnchor(getHeatmapTable(), profileIndex + 1);
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

        By byTooltipClass = By.className("genename-tooltip");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public List<String> getGenePropertyTooltipHighlightedTerms(int zeroBasedProfileIndex) {
        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//span[@class='highlight']");
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


        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("accordion")));

        return new BioEntityPage(driver);
    }
}
