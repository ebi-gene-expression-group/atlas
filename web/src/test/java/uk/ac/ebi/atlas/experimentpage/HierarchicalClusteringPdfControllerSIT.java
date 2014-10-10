package uk.ac.ebi.atlas.experimentpage;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;

public class HierarchicalClusteringPdfControllerSIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    @Test
    public void clusteringPdfButton_SingleSpeciesExperiment() {
        subject = new HeatmapTablePage(driver, "E-MTAB-513", "");
        subject.get();

        String pdfDownloadLink = subject.clickClusteringPdfButtonLink();
        assertThat(pdfDownloadLink, endsWith("E-MTAB-513-heatmap.pdf"));

        Response response = get(pdfDownloadLink);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/pdf");
    }

    @Test
    public void clusteringPdfButton_MultiSpeciesExperiment() {
        subject = new HeatmapTablePage(driver, "E-GEOD-30352", "serializedFilterFactors=ORGANISM:Mus%20musculus");
        subject.get();

        String pdfDownloadLink = subject.clickClusteringPdfButtonLink();
        assertThat(pdfDownloadLink, endsWith("E-GEOD-30352_mus_musculus-heatmap.pdf"));

        Response response = get(pdfDownloadLink);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/pdf");
    }

}