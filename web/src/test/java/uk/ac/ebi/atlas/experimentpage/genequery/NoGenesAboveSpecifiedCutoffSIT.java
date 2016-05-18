
package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

public class NoGenesAboveSpecifiedCutoffSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION, "serializedFilterFactors=&queryFactorType=&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=true&geneQuery=U3&_queryFactorValues=1&specific=true&_specific=on&cutoff=50");
        subject.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDisplayErrorMessage() {
        assertThat(subject.getPreferencesErrors(), startsWith("No genes found"));
    }

    @Test
    public void shouldNotDisplayHeatmapMessage() {
        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

}
