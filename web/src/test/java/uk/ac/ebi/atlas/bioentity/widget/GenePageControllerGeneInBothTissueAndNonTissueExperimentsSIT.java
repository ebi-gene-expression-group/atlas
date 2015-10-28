package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerGeneInBothTissueAndNonTissueExperimentsSIT extends SingleDriverSeleniumFixture {

    private static BioEntitiesPage subject;

    @BeforeClass
    public static void getStartingPage() {
        subject = new BioEntitiesPage(SingleDriverSeleniumFixture.create(), "genes/ENSG00000005194");
        subject.get();
    }

    @Test
    public void paneHeader() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));
    }

    @Test
    public void baselineWidget() {
        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 4 of 4 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        assertThat(subject.getGeneNames().size(), is(4));
        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues", "Human Proteome Map - adult", "Human Proteome Map - fetus"));
        assertThat(subject.hasAnatomogram(), is(true));
    }

    @Test
    public void baselineResults() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResultsWithoutSpecies();

        assertThat(baselineCounts, hasSize(131));

        assertThat(baselineCounts.get(0).getExperimentAccession(), is("dummy-E-MTAB-2706"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Cell Lines - 675 Genentech - B-cell lymphoma, blood"));
        assertThat(baselineCounts.get(0).getSpecies(), is(nullValue()));
        assertThat(baselineCounts.get(0).getHref(), endsWith("/experiments/dummy-E-MTAB-2706?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=ENSG00000005194&exactMatch=true&serializedFilterFactors=DISEASE:B-cell%20lymphoma,ORGANISM_PART:blood"));
    }

    @Test
    public void hiddenBaselineResults() {
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(10));
        subject.clickMoreBaselineResults();
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(131));
    }

}
