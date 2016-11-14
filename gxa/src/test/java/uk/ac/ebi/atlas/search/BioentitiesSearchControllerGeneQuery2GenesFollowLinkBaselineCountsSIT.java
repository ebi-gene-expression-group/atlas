
package uk.ac.ebi.atlas.search;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class BioentitiesSearchControllerGeneQuery2GenesFollowLinkBaselineCountsSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=TERF2%09GFI1");
        subject.get();
    }

    @Test
    public void multipleGenesQueryInMultipleSpecies_followingBaselineCountsLinkShouldGoToExperimentPageWithResults() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();

        assertThat(baselineCounts, hasSize(4));

        //click link to go to experiment page for 27 tissues
        subject.getBaselineCountElements().get(1).click();

        HeatmapTablePage experimentPage = new HeatmapTablePage(driver, null);

        List<String> geneNames = experimentPage.getGeneNames();
        Assert.assertThat(geneNames, contains("TERF2", "GFI1"));
    }

}
