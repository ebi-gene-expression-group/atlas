
package uk.ac.ebi.atlas.experimentpage.genequery;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

public class GeneNotFoundSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION, "geneQuery=cdnjdsnfdksjfbc&serializedFilterFactors=&queryFactorType=&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Test
    public void shouldDisplayErrorMessage() {
        assertThat(subject.getPreferencesErrors(), startsWith("No genes found"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDisplayHeatmapMessage() {
        subject.getHeatmapMessage();
    }

}
