package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class HeatmapTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/gxa/experiment";

    public HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @FindBy(id = "heatmap-table")
    WebElement heatmapTable;

    public List<String> getOrganismParts() {
        List<String> organismParts = getTableHeaders(heatmapTable);
        //and we need to remove the last header value, because is related to the organism part column
        return organismParts.subList(1, organismParts.size());
    }

    public List<String> getSelectedGenes() {
        return getFirstColumnValues(heatmapTable);
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

}
