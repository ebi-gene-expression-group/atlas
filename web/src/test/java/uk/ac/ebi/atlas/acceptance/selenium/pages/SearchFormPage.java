package uk.ac.ebi.atlas.acceptance.selenium.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SearchFormPage extends AtlasPage {

    protected static final String DEFAULT_PAGE_URI = "/gxa/experiment";

    @FindBy(id = "geneIDsString")
    WebElement geneIDsString;

    @FindBy(id = "organismParts")
    WebElement organismParts;

    @FindBy(id = "cutoff")
    WebElement cutoff;

    public SearchFormPage(WebDriver driver) {
        super(driver);
    }

    public SearchFormPage(WebDriver driver, String parameters) {
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

}