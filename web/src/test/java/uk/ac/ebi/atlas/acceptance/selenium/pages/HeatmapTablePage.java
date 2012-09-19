package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;


public class HeatmapTablePage extends TablePage {

    private static final String DEFAULT_PAGE_URI = "/atlas/experiment";

    @FindBy(id = "heatmapTable")
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
        return getLastColumnValues(heatmapTable);
    }

    public String getTitle() {
        return driver.getTitle();
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

}
