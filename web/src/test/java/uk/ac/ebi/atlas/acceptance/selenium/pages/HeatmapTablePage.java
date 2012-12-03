package uk.ac.ebi.atlas.acceptance.selenium.pages;

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

    @FindBy(id="geneCount")
    private WebElement geneFound;

    @FindBy(id = "download-profiles-link")
    private WebElement downloadExpressionProfilesLink;

    @FindBy(id = "display-levels")
    private WebElement displayLevelsButton;

    @FindBy(className = "gradient-level")
    private List<WebElement> gradientLevels;

    public HeatmapTablePage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    protected WebElement getHeatmapTable(){
        return heatmapTable;
    }

    public List<String> getOrganismParts() {
        List<String> organismParts = getTableHeaders(heatmapTable);
        //and we need to remove the last header value, because is related to the organism part column
        return organismParts.subList(1, organismParts.size());
    }

    public List<String> getSelectedGenes() {
        return getFirstColumnValues(heatmapTable);
    }

    public String getDownloadExpressionProfilesLink(){
        return downloadExpressionProfilesLink.getAttribute("href");
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public List<String> getFirstGeneProfile() {
        List<String> firstTableRow = getRowValues(heatmapTable,1);
        return firstTableRow.subList(1, firstTableRow.size());
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
        String style = gradientLevels.get(0).getAttribute("style");
        return style.contains("color") && style.contains("white");
    }

    public Boolean areExpressionLevelsHidden() {
        //we get the cell at index 1 because at index 0 we have the gene name
        WebElement firstExpressionLevelCell = this.getNonEmptyCellsFromFirstTableRow(heatmapTable).get(1);
        WebElement div = firstExpressionLevelCell.findElement(By.tagName("div"));
        return !div.getAttribute("style").contains("white");
    }
}
