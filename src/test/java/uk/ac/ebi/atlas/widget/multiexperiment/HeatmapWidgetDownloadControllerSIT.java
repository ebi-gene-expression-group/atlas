package uk.ac.ebi.atlas.widget.multiexperiment;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class HeatmapWidgetDownloadControllerSIT extends RestAssuredFixture {

    @Test
    public void contrastLinesExperiments(){
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/multiExperiment.tsv?geneQuery=ENSG00000026103&propertyType=bioentity_identifier&species=homo%20sapiens");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().header("Content-Disposition", containsString("attachment; filename=\"ENSG00000026103_baseline.tsv\""));

        response.then().assertThat().body(containsString("# Query: Experiments with expression for ENSG00000026103 exactly, given the False Discovery Rate cutoff: 0.05"));
        response.then().assertThat().body(containsString("Twenty seven tissues"));
        response.then().assertThat().body(containsString("Vertebrate tissues"));
    }

    @Test
    public void nonExistingGene(){
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/multiExperiment.tsv?geneQuery=foo&species=homo%20sapiens");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().header("Content-Length", is("0"));
    }

}