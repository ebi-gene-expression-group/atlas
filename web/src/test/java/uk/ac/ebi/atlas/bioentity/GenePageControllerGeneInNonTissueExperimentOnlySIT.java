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
        subject = new BioEntitiesPage(driver, "genes/ENSG00000020219");
        subject.get();
    }

    @Test
    public void baselineResults() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("1 result"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(3)); //including geneset description

        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-GEOD-26284"));
        assertThat(baselineCounts.get(2).getExperimentName(), is("ENCODE cell lines - long non-polyA RNA, whole cell"));
        assertThat(baselineCounts.get(2).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(2).getHref(), endsWith("/experiments/E-GEOD-26284?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=&geneQuery=ENSG00000020219&exactMatch=true&serializedFilterFactors=RNA:long%20non-polyA%20RNA,CELLULAR_COMPONENT:whole%20cell"));
    }

}
