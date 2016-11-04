package uk.ac.ebi.atlas.widget.baselineanalytics;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class HeatmapWidgetDownloadControllerBaselineAnalyticsSIT extends RestAssuredFixture {


    @Test
    public void singleSpeciesGeneAccessionWithExpression() {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics.tsv?geneQuery=ENSG00000163331&species=Homo%20sapiens");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().header("Content-Disposition", containsString("attachment; filename=\"ENSG00000163331_baseline.tsv\""));

        response.then().assertThat().body(containsString("# Query: Experiments with expression for ENSG00000163331 exactly, given the False Discovery Rate cutoff: 0.05"));
        response.then().assertThat().body(containsString("Twenty seven tissues"));
        response.then().assertThat().body(containsString("Vertebrate tissues"));
    }

    @Test
    public void multiSpeciesGeneQuery_Tissues() {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics.tsv?geneQuery=blood&species=Homo%20sapiens");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().header("Content-Disposition", containsString("attachment; filename=\"blood_baseline.tsv\""));

        response.then().assertThat().body(containsString("# Query: Experiments with expression for blood exactly, given the False Discovery Rate cutoff: 0.05"));
        response.then().assertThat().body(containsString("Thirty two tissues"));
        response.then().assertThat().body(containsString("Twenty seven tissues"));
    }

    @Test
    public void cellLine() {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics.tsv?geneQuery=blood&species=Homo%20sapiens&source=CELL_LINE");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().header("Content-Disposition", containsString("attachment; filename=\"blood_baseline.tsv\""));

        response.then().assertThat().body(containsString("# Query: Experiments with expression for blood exactly, given the False Discovery Rate cutoff: 0.05"));
        response.then().assertThat().body(containsString("ENCODE cell lines - long polyA RNA, whole cell"));
        response.then().assertThat().body(containsString("ENCODE cell lines - long polyA RNA, cytosol"));
        response.then().assertThat().body(containsString("ENCODE cell lines - long polyA RNA, nucleus"));
        response.then().assertThat().body(containsString("ENCODE cell lines - long non-polyA RNA, nucleus"));
        response.then().assertThat().body(containsString("ENCODE cell lines - total RNA, whole cell"));
    }

}