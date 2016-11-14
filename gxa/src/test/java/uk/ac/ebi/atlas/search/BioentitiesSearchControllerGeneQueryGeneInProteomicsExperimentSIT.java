
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class BioentitiesSearchControllerGeneQueryGeneInProteomicsExperimentSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ITGA3");
        subject.get();
    }

    @Test
    public void baselinePaneResultsMessage() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("117 results"));
    }

    @Test
    public void baselinePaneResultsIncludeProteomicsExperiments() {
        subject.clickMoreBaselineResults();
        assertThat(subject.getBaselinePaneContents(), containsString("Human Proteome Map - adult"));
        assertThat(subject.getBaselinePaneContents(), containsString("Human Proteome Map - fetus"));
    }

}
