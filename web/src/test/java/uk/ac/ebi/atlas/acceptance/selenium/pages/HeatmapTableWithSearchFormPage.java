package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HeatmapTableWithSearchFormPage extends HeatmapTablePage {

    protected static final String DEFAULT_PAGE_URI = "/gxa/experiments/E-MTAB-513";

    @FindBy(id = "geneIDsString")
    WebElement geneIDsString;

    @FindBy(id = "organismParts")
    WebElement organismParts;

    @FindBy(id = "cutoff")
    WebElement cutoff;

    @FindBy(id = "submit-button")
    WebElement submitButton;

    public HeatmapTableWithSearchFormPage(WebDriver driver) {
        super(driver);
    }

    public HeatmapTableWithSearchFormPage(WebDriver driver, String parameters) {
        super(driver, parameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public String getGeneIDsString() {
        return geneIDsString.getText();
    }

    public String getCutoff() {
        return cutoff.getAttribute("value");
    }

    public List<String> getOrganismParts() {
        Select organismPartsSelect = new Select(organismParts);
        List<String> selectedValues = new ArrayList<String>();
        for (WebElement webElement: organismPartsSelect.getAllSelectedOptions()){
            selectedValues.add(webElement.getAttribute("value"));
        }
        return selectedValues;
    }

    public HeatmapTableWithSearchFormPage submit(){
        submitButton.click();
        return new HeatmapTableWithSearchFormPage(driver);
    }

    public void setCutoff(double value) {
        cutoff.clear();
        cutoff.sendKeys("" + value);
    }
}