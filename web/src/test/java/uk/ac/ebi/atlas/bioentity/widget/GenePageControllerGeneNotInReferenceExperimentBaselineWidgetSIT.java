
package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerGeneNotInReferenceExperimentBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSMUSG00000062154";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void baselineWidgetGenes() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 1 of 1 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        List<String> factorValueHeaders = subject.getFactorValueHeaders();
        //System.out.println("\"" + Joiner.on("\", \"").join(factorValueHeaders) + "\"");
        assertThat(factorValueHeaders, contains("brain", "cerebellum", "heart", "kidney", "liver", "testis"));

        assertThat(subject.getGeneNames().size(), is(1));
        assertThat(subject.getGeneNames(), contains("Vertebrate tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSMUSG00000062154&serializedFilterFactors=ORGANISM%3AMus%20musculus"));
    }


}
