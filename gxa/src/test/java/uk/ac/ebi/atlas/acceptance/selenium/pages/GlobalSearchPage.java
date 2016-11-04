
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class GlobalSearchPage extends AtlasPage {

    @FindBy(id = "local-searchbox")
    private WebElement globalSearchBox;

    @FindBy(id = "submit-searchbox")
    private WebElement globalSearchSubmit;

    @FindBy(id = "error-content")
    private WebElement errorContent;

    GlobalSearchPage(WebDriver driver) {
        this(driver, null);
    }

    public GlobalSearchPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    public BioEntityPage globalSearchSubmit(){
        getGlobalSearchSubmit().click();
        return new BioEntityPage(driver);
    }

    public boolean isResourceNotFound(){
        return "Resource not found.".equals(errorContent.getText());
    }

    public WebElement getGlobalSearchSubmit() {
        return globalSearchSubmit;
    }

    public void setGlobalSearchText(String value) {
        globalSearchBox.clear();
        globalSearchBox.sendKeys(value);
    }
}
