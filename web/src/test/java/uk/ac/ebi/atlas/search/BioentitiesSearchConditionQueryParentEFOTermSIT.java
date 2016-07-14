
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.search.SearchTestUtil.selectResult;

public class BioentitiesSearchConditionQueryParentEFOTermSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "condition=anatomy+basic+component");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        subject.clickMoreBaselineResults();
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(115));

        BaselineBioEntitiesSearchResult result = selectResult(baselineCounts, "E-MTAB-1733");

        assertThat(result.getExperimentName(), is("Twenty seven tissues"));
        assertThat(result.getSpecies(), is("Homo sapiens"));
        assertThat(result.getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery="));
    }

}
