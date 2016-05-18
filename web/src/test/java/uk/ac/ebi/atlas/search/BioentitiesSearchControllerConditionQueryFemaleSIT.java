
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectFirstResult;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchControllerConditionQueryFemaleSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "condition=female");
        subject.get();
    }


    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(71));
        BaselineBioEntitiesSearchResult result = selectFirstResult(baselineCounts, "dummy-E-MTAB-2706");
        assertThat(result.getExperimentName(), is("Cell Lines - 675 Genentech - B-cell lymphoma, lymph node"));
        assertThat(result.getSpecies(), is("Homo sapiens"));
        assertThat(result.getHref(), endsWith("experiments/dummy-E-MTAB-2706?_specific=on&queryFactorType=CELL_LINE&queryFactorValues=OCI-LY-19,RI-1,SU-DHL-5,Toledo&geneQuery=&exactMatch=true&serializedFilterFactors=DISEASE:B-cell%20lymphoma,ORGANISM_PART:lymph%20node"));

    }

    @Test
    public void checkDifferentialProfilesCount() {
        subject.clickDifferentialPane();
        assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 1233 results"));
    }

}
