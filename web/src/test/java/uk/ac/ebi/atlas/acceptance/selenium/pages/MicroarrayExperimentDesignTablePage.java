
package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

public class MicroarrayExperimentDesignTablePage extends DifferentialExperimentDesignTablePage {

    private String experimentAccession = "E-MTAB-1066";

    public MicroarrayExperimentDesignTablePage(WebDriver driver) {
        super(driver);
    }

    public MicroarrayExperimentDesignTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return "/gxa/experiments/" + experimentAccession + "/experiment-design";
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

}