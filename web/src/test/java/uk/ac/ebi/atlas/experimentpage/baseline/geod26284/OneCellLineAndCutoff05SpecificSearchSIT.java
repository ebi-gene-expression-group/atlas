
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class OneCellLineAndCutoff05SpecificSearchSIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(),
                "serializedFilterFactors=CELLULAR_COMPONENT%3Awhole+cell%2CRNA%3Atotal+RNA&queryFactorType=&heatmapMatrixSize=50&displayGeneDistribution=false&geneQuery=&queryFactorValues=CD34-positive+mobilized+cell+cell+line&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"MPO", "PROM1", "DEF6", "MATK", "ITGA2B", "CD74", "CD79B", "WAS", "PRKCH"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive…", "HFDPC cell line", "HPC-PL cell…", "IMR-90", "hMSC-AT cell…"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"144", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"14", "", "", "", ""};
    }

    @Override
    protected String getGeneCount() {
        return "152";
    }
}