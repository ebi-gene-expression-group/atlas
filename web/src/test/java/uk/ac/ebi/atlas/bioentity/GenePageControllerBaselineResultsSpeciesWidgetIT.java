package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;


public class GenePageControllerBaselineResultsSpeciesWidgetIT extends SinglePageSeleniumFixture {

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
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
    }

    @Test
    public void checkGeneProfiles() {
        assertThat(subject.getGeneNames(), contains("ASPM"));
        assertThat(subject.getGeneNames().size(), is(1));
    }

}
