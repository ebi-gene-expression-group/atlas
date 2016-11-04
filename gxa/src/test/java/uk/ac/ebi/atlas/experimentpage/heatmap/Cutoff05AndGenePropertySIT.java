
package uk.ac.ebi.atlas.experimentpage.heatmap;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class Cutoff05AndGenePropertySIT extends SinglePageSeleniumFixture {

    private final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
//
    }

    @Test
    public void verifyResultOnSinglePropertyQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 263"));
    }

    @Test
    public void verifyResultOnMultiplePropertyQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&cutoff=0.5");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 263"));
    }

    @Test
    public void verifyResultOnMultiplePropertyAndOrganismPartQuery() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "geneQuery=&queryFactorValues=skeletal+muscle&queryFactorValues=thyroid&_queryFactorValues=2&cutoff=0.5");
        subject.get();

        assertThat(subject.getGeneCount(), containsString("of 15"));
        subject.clickDisplayLevelsButton();
        List<String> geneProfile = subject.getFirstGeneProfile();
        assertThat(geneProfile, hasItems("0.6","","","1","","","0.6","0.9","1","","","1","","8","8","","3" ));
    }

}
