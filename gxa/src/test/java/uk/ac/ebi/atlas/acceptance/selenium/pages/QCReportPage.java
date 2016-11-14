package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class QCReportPage extends TablePage {

    private static final String PAGE_URI_TEMPLATE = "/gxa/experiments/{0}/qc/{1}/index.html";

    private final String experimentAccession;
    private final String arrayDesign;

    public QCReportPage(WebDriver driver, String experimentAccession, String arrayDesign) {
        this(driver, experimentAccession, arrayDesign, null);
    }

    public QCReportPage(WebDriver driver, String experimentAccession, String arrayDesign, String queryString) {
        super(driver, queryString);
        this.experimentAccession = experimentAccession;
        this.arrayDesign = arrayDesign;
    }

    @Override
    protected String getPageURI() {
        return MessageFormat.format(PAGE_URI_TEMPLATE, experimentAccession, arrayDesign);
    }

}
