
package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod3307;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class ExperimentPageHeaderSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-3307";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void testTitle() {
        assertThat(subject.getExperimentDescription(), startsWith("Transcription profiling by array of 12 human muscle diseases"));
    }

    @Test
    public void testOrganisms() {
        assertThat(subject.getExperimentOrganisms(), is("Organism(s): Homo sapiens"));
    }

    @Test
    public void testArrayDesigns() {
        assertThat(subject.getExperimentArrayDesigns(), is("Array Design(s): Affymetrix GeneChip Human Genome HG-U133A [HG-U133A] Affymetrix GeneChip Human Genome HG-U133B [HG-U133B]"));
    }

}
