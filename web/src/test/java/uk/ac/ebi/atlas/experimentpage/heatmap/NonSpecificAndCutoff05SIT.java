
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NonSpecificAndCutoff05SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=&cutoff=0.5"
            + "&specific=false";

    private static final String HIGHER_RANKING_GENE = "ARPC5";
    private static final String LOWER_RANKING_GENE = "RAB13";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void averageFpkmShouldDetermineRanking() {

        //given
        subject.clickDisplayLevelsButton();

        double higherRankingGeneAverageFpkm = subject.getAverageFpkm(10);
        double lowerRankingGeneAverageFpkm = subject.getAverageFpkm(11);

        //then
        assertThat(higherRankingGeneAverageFpkm, is(89.4375));
        assertThat(higherRankingGeneAverageFpkm, is(greaterThan(lowerRankingGeneAverageFpkm)));

        //and max fpkm is greater for gene at row 11 than gene at row 10
        assertThat(subject.getMaxExpressionLevel(11), is(lessThan(subject.getMaxExpressionLevel(10))));

        //gene at row 11 follows gene at row 10
        assertThat(subject.getGeneThatRanksAt(10), is(HIGHER_RANKING_GENE));
        assertThat(subject.getGeneThatRanksAt(11), is(LOWER_RANKING_GENE));

    }

}
