
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewBioentityPage extends HeatmapTableWidgetPage {

    static final String PAGE_LOCATION = "/gxa/new/";

    private String bioentityIdentifier;

    private String type;

    @FindBy(id = "gxaSearchHeaderSection")
    private WebElement searchSection;

    @FindBy(id = "gxaBioentityHeaderSection")
    private WebElement headerSection;

    @FindBy(id = "gxaBioentityTabsSection")
    private WebElement tabsSection;

    public NewBioentityPage(WebDriver driver) {
        super(driver, null);
    }

    public NewBioentityPage(WebDriver driver, String bioentityIdentifier, String type) {
        super(driver, null);
        this.bioentityIdentifier = bioentityIdentifier;
        this.type = type;
    }

    public NewBioentityPage(WebDriver driver, String bioentityIdentifier, String type, String httpParameters) {
        super(driver, null, httpParameters);
        this.bioentityIdentifier = bioentityIdentifier;
        this.type = type;
    }
}