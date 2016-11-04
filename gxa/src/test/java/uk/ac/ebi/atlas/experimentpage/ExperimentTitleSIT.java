
package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class ExperimentTitleSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void testTitle() {
        assertThat(subject.getExperimentDescription(), startsWith("RNA-Seq of human individual tissues and mixture of 16 " +
                "tissues (Illumina Body Map)"));
    }

    @Test
    public void testOrganisms() {
        assertThat(subject.getExperimentOrganisms(), is("Organism(s): Homo sapiens"));
    }

    @Test
    public void testReferences() {
        assertThat(subject.getExperimentReferences(), is("Reference(s): 22496456 (Filter by genes in paper)     22955988 (Filter by genes in paper)     23258890 (Filter by genes in paper)    "));
    }

}
