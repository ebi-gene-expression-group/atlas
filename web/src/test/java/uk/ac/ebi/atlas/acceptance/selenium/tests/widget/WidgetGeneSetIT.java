package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        // wait for ajax widget to load
        SeleniumUtil.waitForElementByIdUntilVisible(driver, "heatmap-div");

        assertThat(subject.isIndividualGenesVisible(), is(true));
        assertThat(subject.isGeneSetProfilesVisible(), is(false));
        subject.clickShowGeneSetProfiles();
        assertThat(subject.isIndividualGenesVisible(), is(false));
        assertThat(subject.isGeneSetProfilesVisible(), is(true));
    }

    @Test
    public void heatmapHasGeneLinks() {
        // wait for ajax widget to load
        SeleniumUtil.waitForElementByIdUntilVisible(driver, "heatmap-div");

        assertThat(subject.getGeneNames(), contains("FASLG", "FAS", "TNFRSF1A", "TNFSF10", "CASP8", "TNFRSF10B", "RIPK1", "ADAM17", "TRADD", "FADD", "TRAF2", "CASP10"));
        assertThat(subject.getGeneNames().size(), is(12));
        assertThat(subject.getGeneLink(0), endsWith("/genes/ENSG00000117560"));
    }

}
