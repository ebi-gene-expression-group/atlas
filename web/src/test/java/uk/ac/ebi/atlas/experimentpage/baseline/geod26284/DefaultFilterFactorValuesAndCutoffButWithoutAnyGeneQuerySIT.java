
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class DefaultFilterFactorValuesAndCutoffButWithoutAnyGeneQuerySIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(), "geneQuery=&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"CALCR", "MUSK", "NPC1L1", "NR1H4", "INSRR", "C8B", "MMP25", "CD6", "LGALS14"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"A549", "AG445", "BJ", "CD20-positive…", "GM12878", "H1-hESC", "HMEC cell line", "HSMM cell line", "HUVEC cell line", "HeLa-S3", "HepG2", "IMR-90", "K562", "MCF-7", "NHEK cell line", "NHLF cell line", "SK-N-SH", "SK-N-SH_RA"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "27", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"", "", "", "", "4", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    }

    @Override
    protected String getGeneCount() {
        return "469";
    }
}