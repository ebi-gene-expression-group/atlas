package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeatmapTableWidgetPageGeneSet extends HeatmapTableWidgetPage {
    private static final String PAGE_LOCATION = "/gxa/widgets/heatmap/protein";

    @FindBy(id = "showIndividualGenes")
    private WebElement showIndividualGenes;

    @FindBy(id = "showGeneSetProfiles")
    private WebElement showGeneSetProfiles;

    @FindBy(id = "heatmap-div")
    private WebElement heatmap;

    @FindBy(id = "heatmap-profilesAsGeneSets")
    private WebElement heatmapProfilesAsGeneSets;

    public HeatmapTableWidgetPageGeneSet(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public void clickShowIndividualGeneProfiles() {
        showIndividualGenes.click();
    }

    public void clickShowGeneSetProfiles() {
        showGeneSetProfiles.click();
    }

    public boolean isIndividualGenesVisible() {
        return heatmap.isDisplayed();
    }

    public boolean isGeneSetProfilesVisible() {
        return heatmapProfilesAsGeneSets.isDisplayed();
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION;
    }

}
