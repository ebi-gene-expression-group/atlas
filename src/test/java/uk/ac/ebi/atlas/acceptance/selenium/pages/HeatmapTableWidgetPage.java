
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeatmapTableWidgetPage extends HeatmapTablePage {
    @FindBy(id = "download-profiles-link")
    private WebElement downloadProfiles;

    private final String pageLocation;

    public HeatmapTableWidgetPage(WebDriver driver, String httpParameters) {
        this(driver, "/widgets/heatmap/referenceExperiment", httpParameters);
    }

    public HeatmapTableWidgetPage(WebDriver driver, String pageLocation, String httpParameters) {
        super(driver, null, httpParameters);
        this.pageLocation = "/gxa" + pageLocation;
    }

    public static HeatmapTableWidgetPage create(WebDriver driver, String httpParameters) {
        return new HeatmapTableWidgetPage(driver, "/widgets/heatmap/bioentity", httpParameters);
    }

    public static HeatmapTableWidgetPage createGenePage(WebDriver driver, String geneId) {
        return new HeatmapTableWidgetPage(driver, "/genes/" + geneId, "");
    }

    public String downloadProfilesLink() {
        return downloadProfiles.getAttribute("href");
    }


    @Override
    protected String getPageURI() {
        return pageLocation;
    }
}