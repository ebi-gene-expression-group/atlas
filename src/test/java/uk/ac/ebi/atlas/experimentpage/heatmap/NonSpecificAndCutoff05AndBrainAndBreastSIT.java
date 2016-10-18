
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NonSpecificAndCutoff05AndBrainAndBreastSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=&cutoff=0.5"
            + "&queryFactorValues=brain&queryFactorValues=breast"
            + "&specific=false";


    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(50));
        assertThat(selectedGenes.get(0), is("TMSB10"));
        assertThat(selectedGenes.get(1), is("RTN4"));
        assertThat(selectedGenes.get(2), is("AC005702.4"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("1728", "2286", "1206", "704", "1344", "1633", "326", "793", "2380", "156", "1825", "1265", "807", "129", "1040", "425"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("188"), is(true));
    }

}
