
package uk.ac.ebi.atlas.search.widget;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class BioentitiesSearchControllerGeneQuery2GenesFollowLinkWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=A1A4S6%09Q13177");
        subject.get();
    }

    @Ignore
    public void multipleGenesQueryInSingleSpeciesTissue_followingWidgetLinkShouldGoToExperimentPageWithResults() {
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), contains("Twenty seven tissues"));

        //click link to go to experiment page
        subject.getGeneAnchor(0).click();

        HeatmapTablePage experimentPage = new HeatmapTablePage(driver, null);

        List<String> geneNames = experimentPage.getGeneNames();
        Assert.assertThat(geneNames, contains("PAK2", "ARHGAP10"));
    }

}
