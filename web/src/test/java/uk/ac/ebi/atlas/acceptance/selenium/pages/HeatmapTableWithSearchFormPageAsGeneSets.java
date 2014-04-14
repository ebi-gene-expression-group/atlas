package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeatmapTableWithSearchFormPageAsGeneSets extends HeatmapTableWithSearchFormPage {

    @FindBy(id = "showIndividualGenes")
    private WebElement showIndividualGenes;

    @FindBy(id = "showGeneSetProfiles")
    private WebElement showGeneSetProfiles;

    @FindBy(id = "heatmap-div")
    private WebElement heatmap;

    @FindBy(id = "heatmap-profilesAsGeneSets")
    private WebElement heatmapProfilesAsGeneSets;

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
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

}
