
package uk.ac.ebi.atlas.search.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQuery2GenesMultipleResultsWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ENSG00000004948%09ENSG00000030304");
        subject.get();
    }

    @Test
    public void displaysWidget() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));

        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-2836?geneQuery=ENSG00000004948%09ENSG00000030304"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-1733?geneQuery=ENSG00000004948%09ENSG00000030304"));
    }

}
