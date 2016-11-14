
package uk.ac.ebi.atlas.experimentpage.heatmap;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpecificAndCutoff05SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "cutoff=0.5"
            + "&specific=true";

    private static final String HIGHER_RANKING_GENE = "ACTL7A";
    private static final String LOWER_RANKING_GENE = "TEX33";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void specificityShouldDetermineRanking() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneFpkm = subject.getMaxExpressionLevel(1);
        double lowerRankingGeneFpkm = subject.getMaxExpressionLevel(2);

        //then
        assertThat(higherRankingGeneFpkm, is(69D));
        assertThat(higherRankingGeneFpkm, is(greaterThan(lowerRankingGeneFpkm)));

        //gene at row 11 follows gene at row 10
        assertThat(subject.getGeneThatRanksAt(1), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(2), is(LOWER_RANKING_GENE));

    }

}
