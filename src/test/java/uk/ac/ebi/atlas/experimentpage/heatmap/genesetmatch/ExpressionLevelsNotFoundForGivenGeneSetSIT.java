
package uk.ac.ebi.atlas.experimentpage.heatmap.genesetmatch;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class ExpressionLevelsNotFoundForGivenGeneSetSIT extends SeleniumFixture {

    private static final String E_E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    protected HeatmapTableWithSearchFormPage subject;

    @Before
    public void initSubject() {
    }

    @Test
    public void givenThatGeneSetTermIsValidButThereAreNoGeneProfilesAboveCutoffThenHeatmapShouldNotBeDisplayed() {
        String cytoplasmWithCutoffHigherThanMaxLevel = "serializedFilterFactors=ORGANISM%3AHomo+sapiens&queryFactorType=ORGANISM_PART" +
                "&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=true&geneQuery=%22Alpha-1-acid+glycoprotein%22+react_1619" +
                "&geneSetMatch=true&_geneSetMatch=on&_queryFactorValues=1&specific=true&_specific=on&cutoff=20000";
        //given
        subject = new HeatmapTableWithSearchFormPage(driver, E_E_GEOD_30352_ACCESSION, cytoplasmWithCutoffHigherThanMaxLevel);
        //when
        subject.get();
        //then
        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));

    }


}
