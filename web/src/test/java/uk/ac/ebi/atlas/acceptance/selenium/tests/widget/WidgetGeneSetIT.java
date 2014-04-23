package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WidgetGeneSetIT extends SeleniumFixture {

    private static final String GENESET = "REACT_1619";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENESET, "genesets", "openPanelIndex=1");
        subject.get();
    }

    @Test
    public void heatmapChangesOnClick() {
        assertThat(subject.isIndividualGenesVisible(), is(true));
        assertThat(subject.isGeneSetProfilesVisible(), is(false));
        subject.clickShowGeneSetProfiles();
        assertThat(subject.isIndividualGenesVisible(), is(false));
        assertThat(subject.isGeneSetProfilesVisible(), is(true));
    }

}
