
package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BioentitiesSearchControllerBaselineResultsPaneSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
    }

    @Test
    public void baselinePaneResultsMessageWidget() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ENSMUSG00000097801%09ENSMUSG00000090429");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));
    }

    @Test
    public void baselinePaneResultsMessageMultipleResults() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ASPM");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("2 results"));
    }

    @Test
    public void baselinePaneResultsMessageNoResults() {
        subject = BioEntitiesPage.search(driver, "geneQuery=foobar");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
    }

    @Test
    public void whenNoBaselinePaneResultsAndDiffResultsThenTheDifferentialPaneIsOpen() {
        subject = BioEntitiesPage.search(driver, "condition=%22Apical%20anaerobic%22");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("2 results"));
        assertThat(subject.isDifferentialPaneExpanded(), is(true));
    }

    @Test
    public void whenNoBaselinePaneResultsAndNoDiffResultsThenNoPaneIsOpen() {
        subject = BioEntitiesPage.search(driver, "condition=foobar");
        subject.get();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
        assertThat(subject.isBaselinePaneExpanded(), is(false));
        assertThat(subject.isDifferentialPaneExpanded(), is(false));
    }
}
