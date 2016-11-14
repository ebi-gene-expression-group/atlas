
package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnatomogramSIT extends SeleniumFixture {

    private static final String E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    private static final String E_GEOD_26284_ACCESSION = "E-GEOD-26284";
    private static final String E_MTAB_513_ACCESSION = "E-MTAB-513";

    private HeatmapTablePage getPage(String experimentAccession, String params){
        HeatmapTablePage heatmapTablePage = new HeatmapTablePage(driver, experimentAccession, params);
        heatmapTablePage.get();
        return heatmapTablePage;
    }

    private boolean isAnatomogramElementFound(String experimentAccession, String params) {
        return getPage(experimentAccession, params).getAnatomogram().isDisplayed();
    }

    private boolean isAnatomogramElementFound(String experimentAccession) {
        return isAnatomogramElementFound(experimentAccession, "");
    }

    @Test
    public void testAnatomogramIsThereForHomoSapiensExp() {
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION), is(true));
    }

    @Test
    public void testAnatomogramIsThereForMultiSpeciesExpForMacacaMulatta() {
        String params = "serializedFilterFactors=ORGANISM:Macaca+mulatta&queryFactorType=ORGANISM_PART&geneQuery=";
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION, params), is(true));
    }

    @Test
    public void testAnatomogramIsThereForMultiSpeciesExpForChicken() {
        String params = "serializedFilterFactors=ORGANISM:Gallus+gallus&queryFactorType=ORGANISM_PART&geneQuery=";
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION, params), is(true));
    }

    @Test
    public void testAnatomogramIsThereForMultiSpeciesExpForHomoSapiens() {
        String homoSapiensParams = "serializedFilterFactors=ORGANISM:Homo+sapiens&queryFactorType=ORGANISM_PART&geneQuery=";
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION, homoSapiensParams), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void testAnatomogramIsNotThereForMultiSpeciesExpForOrganismPartFiltering() {
        String nullSpeciesParams = "serializedFilterFactors=ORGANISM_PART:liver&queryFactorType=ORGANISM&geneQuery=";
        isAnatomogramElementFound(E_GEOD_30352_ACCESSION, nullSpeciesParams);
    }

    @Test(expected = NoSuchElementException.class)
    public void testAnatomogramIsNotThereForCellTypeExperiment() {
        isAnatomogramElementFound(E_GEOD_26284_ACCESSION);
    }

    @Test
    public void testAnatomogramIsThereForSingleSpeciesExp() {
        assertThat(isAnatomogramElementFound(E_MTAB_513_ACCESSION), is(true));
    }
}
