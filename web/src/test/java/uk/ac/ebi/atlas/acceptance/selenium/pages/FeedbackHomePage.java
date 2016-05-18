
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

public class FeedbackHomePage extends FeedbackPage {

    private static final String DEFAULT_PAGE_URI = "/gxa/home";

    public FeedbackHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

}