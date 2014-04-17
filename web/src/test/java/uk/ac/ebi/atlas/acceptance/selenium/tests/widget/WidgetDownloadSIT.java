package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WidgetDownloadSIT extends SeleniumFixture {

    private static final String GENESET = "REACT_1619";

    private HeatmapTableWidgetPage widget;

    @Before
    public void initPage(){
        widget = new HeatmapTableWidgetPage(driver, "geneQuery=" + GENESET);
        widget.get();
    }

    @Test
    public void heatmapChangesOnClick() {
        assertThat(widget.downloadProfilesLink(), is("http://172.22.69.130:8080/gxa/experiments/E-MTAB-1733.tsv?geneQuery=REACT_1619"));
    }

}
