package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

public class BioEntitiesPage extends BioEntityPage {

    private String query;

    public BioEntitiesPage(WebDriver driver) {
        super(driver);
    }

    public BioEntitiesPage(WebDriver driver, String query) {
        super(driver);
        this.query = query;
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + "query?condition=" + query;
    }

    @Override
    protected int getContrastColumnIndex() {
        return 4;
    }

}

