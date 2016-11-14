
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class WholeCellAndA549AndCutoffButWithoutAnyGeneQuerySIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(),
                "geneQuery=&serializedFilterFactors=CELLULAR_COMPONENT%3Awhole+cell%2CCELL_LINE%3AA549&queryFactorType=RNA&displayLevels=true");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "RNA";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"ZBTB32", "NME2", "ARHGAP31", "CLDN11", "GPR124", "ABCC8", "PHF7", "PGLYRP1", "CCDC88C"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"long non-polyA…", "long polyA RNA"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"2", ""};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"0.9", ""};
    }

    @Override
    protected String getGeneCount() {
        return "369";
    }

}