
package uk.ac.ebi.atlas.experimentpage.baseline.geod30352;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HeatmapTableForDifferentOrganismsAndDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    public static final String ORGANISM_PART = "Organism part";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPanPaniscus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pan%20paniscus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPanTroglodytes() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pan%20troglodytes");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForPongoPygmaeus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Pongo%20pygmaeus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(5));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForGallusGallus() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Gallus%20gallus");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForGorillaGorilla() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Gorilla%20gorilla");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForHomoSapiens() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Homo%20sapiens");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(8));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));
    }

    @Test
    public void verifyGeneExpressionLevelOfNAIsNotShown() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Homo%20sapiens&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(8));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("cerebellum"));

        List<String> geneNames = subject.getGeneNames();
        assertThat(geneNames, hasItem("TGDS"));

        int tgdsPosition = geneNames.indexOf("TGDS");
        List<String> tgds = subject.getGeneProfile(tgdsPosition + 1);

        assertThat(tgds, contains("1", "1", "1", "2", "", "2", "1", "3"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForMacacaMulatta() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Macaca%20mulatta");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeadersForMonodelphisDomestica() {
        subject = new HeatmapTablePage(driver, E_GEOD_30352_ACCESSION, "serializedFilterFactors=ORGANISM:Monodelphis%20domestica");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is(ORGANISM_PART));

        assertThat(subject.getFactorValueHeaders().size(), is(6));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("brain"));
    }

}
