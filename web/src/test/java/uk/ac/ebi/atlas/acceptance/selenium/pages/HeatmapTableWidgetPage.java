package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

public class HeatmapTableWidgetPage extends HeatmapTablePage{
    private static final String PAGE_LOCATION = "/gxa/heatmap-widget/protein/";

    private String uniprotAccession;

    public HeatmapTableWidgetPage(WebDriver driver, String experimentAccession) {
        this(driver, experimentAccession, null);
    }

    public HeatmapTableWidgetPage(WebDriver driver, String uniprotAccession, String httpParameters) {
        super(driver, uniprotAccession, httpParameters);
        this.uniprotAccession = uniprotAccession;
    }

    @Override
    protected String getPageURI() {
        return PAGE_LOCATION + uniprotAccession;
    }
}
