package uk.ac.ebi.atlas.search.widget;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;


public class BioentitiesSearchControllerOrganismQueryWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ASPM&exactMatch=true&_exactMatch=on&organism=Homo+sapiens&condition=");
        subject.get();
    }

    @Test
    public void checkBaselinePaneHeader() {
        assertThat(subject.getSearchResultsHeader(), is("Expression Atlas results for ASPM AND Homo sapiens"));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
    }

    @Test
    public void baselineWidgetGenes() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));

        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneCount(), is("Showing 1 of 1 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        assertThat(subject.getGeneNames(), IsIterableContainingInOrder.contains("Twenty seven tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-1733?geneQuery=ASPM"));
    }

}
