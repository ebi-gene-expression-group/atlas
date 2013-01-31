package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public abstract class AtlasPage extends LoadableComponent<AtlasPage> {

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
        if (StringUtils.isBlank(hostname)){
            System.out.println("selenium.test.host is null, so tests will be executed against local machine");
            hostname = getLocalHostAddress();
        }

        System.out.println("running tests on local host address: " + hostname);

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
        System.out.println("<buildURL> Running on page: " + stringBuilder.toString());
        return stringBuilder.toString();

    }

    private String getLocalHostAddress(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
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
