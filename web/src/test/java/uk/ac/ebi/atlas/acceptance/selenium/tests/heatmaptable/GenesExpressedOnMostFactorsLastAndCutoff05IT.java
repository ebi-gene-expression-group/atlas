package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GenesExpressedOnMostFactorsLastAndCutoff05IT extends SeleniumFixture {

    private static final String HTTP_PARAMETERS = "cutoff=0.5"
            + "&specific=false";

    private static final String HIGHER_RANKING_GENE = "VPS4A";
    private static final String LOWER_RANKING_GENE = "PPT1";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void averageFpkmShouldDetermineRanking() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneAverageFpkm = subject.getAverageFpkm(10);
        double lowerRankingGeneAverageFpkm = subject.getAverageFpkm(11);

        //then
        assertThat(higherRankingGeneAverageFpkm, is(47.0D));
        assertThat(higherRankingGeneAverageFpkm, is(greaterThan(lowerRankingGeneAverageFpkm)));

        //and even though max fpkm is greater for gene at row 11 than gene at row 10
        assertThat(subject.getMaxFpkm(11), is(greaterThan(subject.getMaxFpkm(10))));

        //gene at row 11 follows gene at row 10 because has higher average!
        assertThat(subject.getGeneThatRanksAt(10), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(11), is(LOWER_RANKING_GENE));

    }

}
