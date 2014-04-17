package uk.ac.ebi.atlas.acceptance.selenium.tests.widget;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

public class WidgetDownloadSIT extends SeleniumFixture {

    private static final String GENESET = "REACT_1619";

    private HeatmapTableWidgetPage widget;

    @Before
    public void initPage(){
        widget = new HeatmapTableWidgetPage(driver, "geneQuery=" + GENESET);
        widget.get();
    }

    @Test
    public void downloadProfilesLink() {
        assertThat(widget.downloadProfilesLink(), endsWith("/gxa/experiments/E-MTAB-1733.tsv?geneQuery=REACT_1619"));
    }

}
