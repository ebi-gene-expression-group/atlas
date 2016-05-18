
package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class SearchFormSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION, "geneQuery=ENSG00000175084+ENSG00000210195&cutoff=540&queryFactorValues=heart&queryFactorValues=liver");
        subject.get();
    }

    @Ignore //TODO fix
    @Test
    public void searchFormContentShouldReflectRequestParameters() {
        assertThat(subject.getGeneQuery(), is("ENSG00000175084 ENSG00000210195"));
        assertThat(subject.getCutoff(), is("540"));
        assertThat(subject.getFactorValueHeaders(), contains("heart", "liver"));
    }


}
