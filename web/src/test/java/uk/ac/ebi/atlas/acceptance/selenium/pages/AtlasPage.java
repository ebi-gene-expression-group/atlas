package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public abstract class AtlasPage extends LoadableComponent<AtlasPage> {

    protected WebDriver driver;

    private URLBuilder urlBuilder;

    private String pageURL;

    AtlasPage(WebDriver driver) {
        this(driver, null);
    }

    AtlasPage(WebDriver driver, String httpParameters) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        urlBuilder = new URLBuilder(getPageURI());
        pageURL = urlBuilder.buildURL(httpParameters);
    }

    @Override
    protected void load() {
        driver.get(pageURL);
    }

    protected abstract String getPageURI();

    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(pageURL));
    }
}
