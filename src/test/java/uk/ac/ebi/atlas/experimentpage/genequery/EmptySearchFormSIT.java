
package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmptySearchFormSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Ignore //TODO fix
    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneQuery(), is("protein_coding"));
        assertThat(subject.getCutoff(), is("0.5"));
        assertThat(subject.getFactorValueHeaders().size(), is(0));
    }


}
