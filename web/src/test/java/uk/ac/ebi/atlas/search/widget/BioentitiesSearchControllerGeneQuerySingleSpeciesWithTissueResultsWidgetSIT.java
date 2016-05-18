
package uk.ac.ebi.atlas.search.widget;

import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.hasResult;

public class BioentitiesSearchControllerGeneQuerySingleSpeciesWithTissueResultsWidgetSIT extends SingleDriverSeleniumFixture {

    private static BioEntitiesPage subject;

    @BeforeClass
    public static void getStartingPage() {
        subject = BioEntitiesPage.search(SingleDriverSeleniumFixture.create(), "geneQuery=zinc+finger&organism=Homo+sapiens");
        subject.get();
    }

    @Test
    public void baselinePaneResultsMessage() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));
    }

    @Test
    public void displaysWidget() {
        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), containsInAnyOrder("Thirty two tissues", "Twenty seven tissues", "Human Proteome Map - adult", "Illumina Body Map"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-2836?geneQuery=zinc+finger"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-1733?geneQuery=zinc+finger"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-MTAB-513?geneQuery=zinc+finger"));
        assertThat(subject.getGeneLink(3), endsWith("/experiments/E-PROT-1?geneQuery=zinc+finger&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
    }

    @Test
    public void baselineResults() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResultsWithoutSpecies();

        // no tissue results
        assertThat(hasResult(baselineCounts, "E-PROT-1"), is(false));
        assertThat(hasResult(baselineCounts, "E-MTAB-513"), is(false));
        assertThat(hasResult(baselineCounts, "E-MTAB-2836"), is(false));
        assertThat(hasResult(baselineCounts, "E-MTAB-1733"), is(false));

        // non tissue results
        assertThat(hasResult(baselineCounts, "E-GEOD-26284"), is(true));
    }

}
