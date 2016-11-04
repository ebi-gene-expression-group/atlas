
package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionSingleBaselineResultSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=REACT_77799%09REACT_115202&condition=DBA%2F2J+x+C57BL%2F6J");
        subject.get();
    }

    @Test
    public void displaysResultsTableBecauseThereIsACondition() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(1));

        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("1 result"));

        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-599"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Tissues - 6"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Mus musculus"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-MTAB-599?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=REACT_77799%09REACT_115202"));
    }

}
