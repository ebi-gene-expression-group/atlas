package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPageGeneSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WidgetGeneSetIT extends SeleniumFixture {

    private static final String GENESET = "REACT_1619";

    private HeatmapTableWidgetPageGeneSet widget;

    @Before
    public void initPage(){
        widget = new HeatmapTableWidgetPageGeneSet(driver, "geneQuery=" + GENESET);
        widget.get();
    }

    @Test
    public void heatmapChangesOnClick() {
        assertThat(widget.isIndividualGenesVisible(), is(true));
        assertThat(widget.isGeneSetProfilesVisible(), is(false));
        widget.clickShowGeneSetProfiles();
        assertThat(widget.isIndividualGenesVisible(), is(false));
        assertThat(widget.isGeneSetProfilesVisible(), is(true));
    }

}
