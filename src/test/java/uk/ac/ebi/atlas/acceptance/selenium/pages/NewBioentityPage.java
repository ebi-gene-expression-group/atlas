
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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