package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;

public class ExperimentAnalysisMethodsPage extends TablePage {

    public static String DEFAULT_EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String PAGE_URI_TEMPLATE = "/gxa/experiments/{0}/analysis-methods";

    private final String experimentAccession;

    public ExperimentAnalysisMethodsPage(WebDriver driver) {
        this(driver, null);
    }

    public ExperimentAnalysisMethodsPage(WebDriver driver, String experimentAccession) {
        super(driver, null);
        this.experimentAccession = experimentAccession == null? DEFAULT_EXPERIMENT_ACCESSION : experimentAccession;
    }

    public ExperimentAnalysisMethodsPage(WebDriver driver, String experimentAccession, String queryString) {
        super(driver, queryString);
        this.experimentAccession = experimentAccession == null? DEFAULT_EXPERIMENT_ACCESSION : experimentAccession;
    }

    @Override
    protected String getPageURI() {
        return MessageFormat.format(PAGE_URI_TEMPLATE, experimentAccession);
    }

}
