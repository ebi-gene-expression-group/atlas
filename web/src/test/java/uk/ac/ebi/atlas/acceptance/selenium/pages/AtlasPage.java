package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

abstract class AtlasPage extends LoadableComponent<AtlasPage> {

    protected WebDriver driver;

    private String pageURL;

    AtlasPage(WebDriver driver) {
        this(driver, null);
    }

    AtlasPage(WebDriver driver, String httpParameters) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageURL = buildURL(httpParameters);
    }


    @Override
    protected void load() {
        driver.get(pageURL);
    }

    String buildURL(String httpParameters){
        String hostname = System.getProperty("selenium.test.host");
        if (hostname == null) {
            hostname = "localhost";
        }
        String portNumber = System.getProperty("selenium.test.portnumber");
        if (portNumber == null) {
            portNumber = "9090";
        }
        StringBuilder stringBuilder = new StringBuilder("http://")
                                            .append(hostname)
                                            .append(":")
                                            .append(portNumber)
                                            .append(getPageURI());
        if (!Strings.isNullOrEmpty(httpParameters)){
            stringBuilder.append("?").append(httpParameters);
        }

        return stringBuilder.toString();

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
