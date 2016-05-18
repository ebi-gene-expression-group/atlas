
package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerBaselineWidgetForSpeciesWithoutReferenceExperimentSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSGGOG00000005112"; //gorilla gorilla

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void baselinePaneHasResultsForGorillaGorilla() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.isBaselinePaneExpanded(), is(true));

        assertThat(subject.getGeneNames().size(), is(1));
        assertThat(subject.getGeneNames(), contains("Vertebrate tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSGGOG00000005112&serializedFilterFactors=ORGANISM%3AGorilla%20gorilla"));

    }

}
