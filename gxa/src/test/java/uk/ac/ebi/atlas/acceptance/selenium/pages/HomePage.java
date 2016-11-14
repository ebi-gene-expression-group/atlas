
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends GlobalSearchPage {

    @FindBy(id = "geneQuery")
    private WebElement geneQuery;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    private static final String DEFAULT_PAGE_URI = "/gxa/home";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public void setGeneQuery(String value) {
        geneQuery.clear();
        geneQuery.sendKeys(value);
    }

    public BioEntitiesPage submitSearch(){
        submitButton.click();
        return new BioEntitiesPage(driver);
    }

}