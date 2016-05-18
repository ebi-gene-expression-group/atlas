
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class BioentitiesSearchControllerConditionQueryNonExactMatchSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=kinase&_exactMatch=on");
        subject.get();
    }


    @Test
    public void baselineResultsLinkIncludesExactMatchFalse() {
        List<BaselineBioEntitiesSearchResult> baselineResults = subject.getAllBaselineResults();
        assertThat(baselineResults.get(1).getHref(), containsString("geneQuery=kinase&exactMatch=false"));
    }

}
