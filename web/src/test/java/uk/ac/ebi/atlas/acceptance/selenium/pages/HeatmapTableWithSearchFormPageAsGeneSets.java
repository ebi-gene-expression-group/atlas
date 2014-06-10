package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HeatmapTableWithSearchFormPageAsGeneSets extends HeatmapTableWithSearchFormPage {

    @FindBy(css = "#heatmap-react > table > tbody > tr > td > a")
    private WebElement showGeneSetProfiles;

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession) {
        super(driver, experimentAccession);
    }

    public HeatmapTableWithSearchFormPageAsGeneSets(WebDriver driver, String experimentAccession, String httpParameters) {
        super(driver, experimentAccession, httpParameters);
    }

    public void clickShowGeneSetProfiles() {
        showGeneSetProfiles.click();
    }

}
