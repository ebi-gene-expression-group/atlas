package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerGeneInNonTissueExperimentOnlySIT extends SeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genes/ENSG00000236118");
        subject.get();
    }

    @Test
    public void baselineResults() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("1 result"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(2)); //including geneset description

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-GEOD-26284"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("ENCODE cell lines - long polyA RNA, whole cell"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(1).getHref(), endsWith("/experiments/E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=ENSG00000236118&exactMatch=true&serializedFilterFactors=RNA:long%20polyA%20RNA,CELLULAR_COMPONENT:whole%20cell"));
    }

}
