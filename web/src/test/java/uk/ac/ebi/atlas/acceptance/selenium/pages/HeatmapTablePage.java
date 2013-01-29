package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class HeatmapTablePage extends TablePage {

    public static String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments/" + EXPERIMENT_ACCESSION;

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

    public HeatmapTablePage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    protected WebElement getHeatmapTable() {
        return heatmapTable;
    }

    public List<String> getQueryFactorValues() {
        List<String> queryFactorValues = getTableHeaders(heatmapTable);
        //and we need to remove the last header value, because is related to the organism part column
        return queryFactorValues.subList(1, queryFactorValues.size());
    }

    public List<String> getSelectedGenes() {
        return getFirstColumnValues(heatmapTable);
    }

    public String getDownloadExpressionProfilesLink() {
        return downloadExpressionProfilesLink.getAttribute("href");
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
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
        return style.contains("color") && style.contains("white") && style2.contains("color") && style2.contains("white");
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

    public double getMaxFpkm(int rowIndex) {
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

    public double getAverageFpkm(int rowIndex, String... factors) {

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
}
