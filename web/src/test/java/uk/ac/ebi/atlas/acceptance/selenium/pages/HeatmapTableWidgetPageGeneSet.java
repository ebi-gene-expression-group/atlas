package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeatmapTableWidgetPageGeneSet extends HeatmapTableWidgetPage {
    private static final String PAGE_LOCATION = "/gxa/widgets/heatmap/protein";

    @FindBy(id = "showConstituentGeneProfiles")
    private WebElement showConstituentGeneProfiles;

    @FindBy(id = "showGeneSetProfiles")
    private WebElement showGeneSetProfiles;

    @FindBy(id = "heatmap-div")
    private WebElement heatmap;

    @FindBy(id = "heatmap-constituentGeneProfiles")
    private WebElement heatmapConstituentGeneProfiles;

    public HeatmapTableWidgetPageGeneSet(WebDriver driver, String httpParameters) {
        super(driver, "geneSetMatch=true&" + httpParameters);
    }

    public void clickShowConstituentGeneProfiles() {
        showConstituentGeneProfiles.click();
    }

    public boolean isGeneSetProfilesVisible() {
        return heatmap.isDisplayed();
    }

    public boolean isConstituentGeneProfilesVisible() {
        return heatmapConstituentGeneProfiles.isDisplayed();
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION;
    }

}
