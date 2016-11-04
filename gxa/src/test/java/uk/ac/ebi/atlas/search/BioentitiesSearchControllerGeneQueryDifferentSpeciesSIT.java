
package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQueryDifferentSpeciesSIT extends SinglePageSeleniumFixture {

    public static final String GENE_QUERY_PARAM = "ENSMUSG00000022255%09ENSG00000109929";
    public static final String GLOBAL_SEARCH_TERM = "ENSMUSG00000022255+OR+ENSG00000109929";


    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=" + GENE_QUERY_PARAM);
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(2));

        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("2 results"));

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Tissues - 6"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Mus musculus"));

        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Homo sapiens"));
    }

    @Test
    public void checkDifferentialDisplaysGeneAndOrganismColumnWithValuesForEachSpecies() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("MTDH", "SC5D", "SC5D", "SC5D", "Mtdh"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains("Homo sapiens", "Homo sapiens", "Homo sapiens", "Homo sapiens", "Mus musculus"));
    }

    @Test
    public void globalSearchTermIsIdentifiersSeparatedByOR() {
        assertThat(subject.getGlobalSearchTerm(), is(GLOBAL_SEARCH_TERM));
    }

}
