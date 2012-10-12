package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeatmapTablePageWithFilters extends HeatmapTablePage {

    @FindBy(id = "geneIDsString")
    WebElement geneIDsString;

    HeatmapTablePageWithFilters(WebDriver driver) {
        super(driver);
    }

    public HeatmapTablePageWithFilters(WebDriver driver, String parameters) {
        super(driver, parameters);
    }

    public String getGeneIDsString() {
        return geneIDsString.getText();
    }
}
