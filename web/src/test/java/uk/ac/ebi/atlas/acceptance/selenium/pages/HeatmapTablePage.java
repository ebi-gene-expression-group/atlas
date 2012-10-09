package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class HeatmapTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/gxa/experiment";

    @FindBy(id = "heatmap-table")
    WebElement heatmapTable;

    private String pageUri = DEFAULT_PAGE_URI;

    HeatmapTablePage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTablePage(WebDriver driver, String parameters) {
        this(driver);
        pageUri = pageUri.concat(parameters);
    }

    public List<String> getOrganismParts() {
        return getFirstColumnValues(heatmapTable);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<String> getSelectedGenes() {
        List<String> geneNames = getTableHeaders(heatmapTable);
        //and we need to remove the last header value, because is related to the organism part column
        return geneNames.subList(1, geneNames.size());
    }

    @Override
    protected String getPageURI() {
        return pageUri;
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(pageUri));
    }

    public List<String> getFirstGeneProfile() {
        return getSecondColumnValues(heatmapTable);
    }

    public List<String> getLastGeneProfile() {
        return getLastColumnValues(heatmapTable);
    }


}
