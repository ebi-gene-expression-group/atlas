package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HeatmapTableWithSearchFormPageAsGeneSets extends HeatmapTableWithSearchFormPage {

    @FindBy(id = "showIndividualGenes")
    private WebElement showIndividualGenes;

    @FindBy(id = "showGeneSetProfiles")
    private WebElement showGeneSetProfiles;

    @FindBy(id = "heatmap-div")
    private WebElement heatmap;

    @FindBy(id = "heatmap-profilesAsGeneSets")
    private WebElement heatmapProfilesAsGeneSets;

    @FindBy(id = "geneSetsCount")
    private WebElement geneSetsCount;

    @FindBy(css = "#heatmap-profilesAsGeneSets #heatmap-table")
    private WebElement heatmapProfilesAsGeneSetsTable;

    @FindBy(css = "#heatmap-profilesAsGeneSets #heatmap-table tr td")
    private List<WebElement> heatmapProfilesAsGeneSetsTableColumn;

    public String getGeneCount() {
        return geneSetsCount.getText();
    }

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public void clickShowGeneSetProfiles() {
        showGeneSetProfiles.click();
    }

    public boolean isGeneSetProfilesVisible() {
        return heatmapProfilesAsGeneSets.isDisplayed();
    }

    public List<String> getGeneSetGeneNames() {
        return getFirstColumnValues(heatmapProfilesAsGeneSetsTable);
    }

    public List<String> getFirstGeneSetProfile() {
        List<String> firstTableRow = getRowValues(heatmapProfilesAsGeneSetsTable, 1);
        return firstTableRow.subList(getGeneExpressionStartingRowIndex(), firstTableRow.size());
    }

    public List<String> getGeneSetProfile(int oneBasedRowIndex) {
        List<String> rowValues = getRowValues(heatmapProfilesAsGeneSetsTable, oneBasedRowIndex);
        return rowValues.subList(getGeneExpressionStartingRowIndex(), rowValues.size());
    }

    public String getGeneColumnHeader() {
        return heatmapProfilesAsGeneSetsTableColumn.get(0).getText();
    }

}
