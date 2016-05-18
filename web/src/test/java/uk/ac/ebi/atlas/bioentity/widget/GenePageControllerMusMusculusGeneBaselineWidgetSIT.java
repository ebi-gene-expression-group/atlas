
package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerMusMusculusGeneBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSMUSG00000040505";

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

        assertThat(subject.getGeneCount(), is("Showing 2 of 2 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        List<String> factorValueHeaders = subject.getFactorValueHeaders();
        //System.out.println("\"" + Joiner.on("\", \"").join(factorValueHeaders) + "\"");
        assertThat(factorValueHeaders, containsInAnyOrder("brain", "cerebellum", "heart", "hippocampus", "kidney", "liver", "lung", "spleen", "testis", "thymus"));

        assertThat(subject.getGeneNames().size(), is(2));
        assertThat(subject.getGeneNames(), contains("Tissues - 6","Vertebrate tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-599?geneQuery=ENSMUSG00000040505"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSMUSG00000040505&serializedFilterFactors=ORGANISM%3AMus%20musculus"));
    }


}
