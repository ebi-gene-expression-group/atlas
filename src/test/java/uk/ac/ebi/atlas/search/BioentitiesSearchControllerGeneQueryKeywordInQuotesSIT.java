
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQueryKeywordInQuotesSIT extends SinglePageSeleniumFixture {

    public static final String GENE_QUERY_PARAM = "%22cofactor+binding%22";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=" + GENE_QUERY_PARAM);
        subject.get();
    }

    @Test
    public void baselineExperimentCountsAreCorrect() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(3));

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("Tissues - 6"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Mus musculus"));

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
    }

    @Test
    public void differentialPaneHasResults() {
        subject.clickDifferentialPane();

        assertThat(subject.diffExpressionResultCount(), is("Showing 16 results"));
        // System.out.println("\"" + Joiner.on("\", \"").join(subject.getDiffHeatmapTableGeneColumn()) + "\"");
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("LMO2", "ASNS", "LMO2", "PHYH", "PHYH", "ACLY", "HIF1AN", "ACLY", "TKT", "UROS", "HIF1AN", "SUCLG1", "SUCLG1", "UROS", "TKT", "HACL1"));

    }

    @Test
    public void differentialPaneHasCorrectGenesAndSpecies() {
        subject.clickDifferentialPane();
        assertThat(subject.getDiffHeatmapTableGeneColumn(), hasItems("ASNS"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), hasItems("Homo sapiens"));
    }

    @Test
    public void globalSearchTermIsCorrectlyFormed() {
        assertThat(subject.getGlobalSearchTerm(), is(GENE_QUERY_PARAM));
    }

}
