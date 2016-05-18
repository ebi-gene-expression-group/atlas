
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class LongPolyARnaAndNucleusAndCutoffButWithoutAnyGeneQuerySIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(), "geneQuery=&serializedFilterFactors=RNA%3Along+polyA+RNA%2CCELLULAR_COMPONENT%3Anucleus&queryFactorType=CELL_LINE&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"FGR", "ATP1A2", "DCN", "CALCR", "NR1H4",
                "C8B", "TFAP2B", "CYP3A43", "TTC22"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"A549", "GM12878", "H1-hESC", "HUVEC cell line", "HeLa-S3", "HepG2", "IMR-90", "K562", "MCF-7", "NHEK cell line", "SK-N-SH"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"", "281", "", "", "", "", "", "", "", "", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"", "", "", "", "", "", "", "", "", "3", ""};
    }

    @Override
    protected String getGeneCount() {
        return "467";
    }
}