package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class HeatmapTablePage extends TablePage {

    private static final String DEFAULT_EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String PAGE_LOCATION = "/gxa/experiments/";

    @FindBy(id = "heatmap-table")
    private WebElement heatmapTable;

    @FindBy(id = "geneCount")
    private WebElement geneFound;

    @FindBy(id = "download-profiles-link")
    private WebElement downloadExpressionProfilesLink;

    @FindBy(id = "display-levels")
    private WebElement displayLevelsButton;

    @FindBy(className = "gradient-level-min")
    private WebElement gradientLevelsMin;

    @FindBy(className = "gradient-level-max")
    private WebElement gradientLevelsMax;

    @FindBy(xpath = "//label[@for='queryFactorValues']")
    private WebElement queryFactorLabel;

    @FindBy(id = "anatomogram")
    private WebElement anatomogram;

    private String experimentAccession = DEFAULT_EXPERIMENT_ACCESSION;

    public HeatmapTablePage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public HeatmapTablePage(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, httpParameters);
        this.experimentAccession = experimentAccession;
    }


    protected WebElement getHeatmapTable() {
        return heatmapTable;
    }

    public List<String> getFactorValueHeaders() {
        List<String> queryFactorValues = getTableHeaders(heatmapTable);
        //and we need to remove the last header value, because is related to the organism part column
        return queryFactorValues.subList(1, queryFactorValues.size());
    }

    public List<String> getSelectedGenes() {
        return getFirstColumnValues(heatmapTable);
    }

    public String getQueryFactorLabel() {
        return queryFactorLabel.getText();
    }

    public String getDownloadExpressionProfilesLink() {
        return downloadExpressionProfilesLink.getAttribute("href");
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + experimentAccession;
    }

    public List<String> getFirstGeneProfile() {
        List<String> firstTableRow = getRowValues(heatmapTable, 1);
        return firstTableRow.subList(1, firstTableRow.size());
    }

    public List<String> getGeneProfile(int rowIndex) {
        List<String> rowValues = getRowValues(heatmapTable, rowIndex);
        return rowValues.subList(1, rowValues.size());
    }


    public List<String> getLastGeneProfile() {
        List<String> firstTableRow = getLastRowValues(heatmapTable);
        return firstTableRow.subList(1, firstTableRow.size());
    }

    public String getGeneCount() {
        return geneFound.getText();
    }

    public void clickDisplayLevelsButton() {
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
        WebElement firstExpressionLevelCell = this.getNonEmptyCellsFromFirstTableRow(heatmapTable).get(1);
        WebElement div = firstExpressionLevelCell.findElement(By.tagName("div"));
        return div.getAttribute("class").contains("hide_cell");
    }

    public double getAverageFpkm(int rowIndex) {
        List<String> stringValues = this.getGeneProfile(rowIndex);
        double averageFpkm = 0D;
        for (String stringValue : stringValues) {
            if (StringUtils.isNotBlank(stringValue)) {
                averageFpkm += Double.parseDouble(stringValue);
            }
        }
        return averageFpkm / stringValues.size();
    }

    public double getMaxExpressionLevel(int rowIndex) {
        List<String> stringValues = this.getGeneProfile(rowIndex);
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
        return getSelectedGenes().get(rowIndex - 1);
    }

    public double getAverageExpressionLevel(int rowIndex, String... factors) {

        List<String> tableHeaders = getTableHeaders(heatmapTable);
        tableHeaders.remove(0); //because this is the display button cell

        List<String> stringValues = this.getGeneProfile(rowIndex);

        double averageFpkm = 0D;
        for (String factor : factors) {
            int columnIndex = tableHeaders.indexOf(factor);
            String stringValue = stringValues.get(columnIndex);
            if (StringUtils.isNotBlank(stringValue)) {
                averageFpkm += Double.parseDouble(stringValue);
            }
        }
        return averageFpkm / factors.length;
    }

    public WebElement getAnatomogram(){
        return anatomogram;
    }

    protected WebElement getGeneProfileCell(int profileIndex, int expressionIndex) {
        return this.getCell(heatmapTable, profileIndex + 1, expressionIndex + 2);
    }

    public String getDifferentialExperimentTooltipTableHeader(int zeroBasedIndex){
        hoverOnElement(getGeneProfileCell(0, 0));

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//th[" + (zeroBasedIndex + 1) + "]");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    public String getDifferentialExperimentTooltipTableCell(int zeroBasedIndex){
        hoverOnElement(getGeneProfileCell(0, 0));

        By byTooltipClass = By.xpath("//div[@class='ui-tooltip-content']//td[" + (zeroBasedIndex + 1) + "]");
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTooltipClass));
        return driver.findElement(byTooltipClass).getText();
    }

    private void hoverOnElement(WebElement webElement){
        Action builder;
        Actions hover = new Actions(driver);
        hover.moveToElement(webElement);
        builder = hover.build();
        builder.perform();
    }
}
