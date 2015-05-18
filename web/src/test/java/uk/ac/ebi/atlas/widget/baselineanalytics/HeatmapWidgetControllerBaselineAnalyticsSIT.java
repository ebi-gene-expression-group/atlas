package uk.ac.ebi.atlas.widget.baselineanalytics;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import java.util.Collection;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class HeatmapWidgetControllerBaselineAnalyticsSIT extends RestAssuredFixture {

    @Test
    public void singleSpeciesGeneAccessionWithExpression() {
        Response response = get("/widgets/heatmap/baselineAnalytics?geneQuery=ENSG00000027644");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");

        JsonPath json = response.jsonPath();

        assertThat((String)json.get("profiles.rows[0].id"), is("E-MTAB-2836"));
        assertThat((String)json.get("profiles.rows[1].id"), is("E-MTAB-1733"));
    }

    @Test
    public void multiSpeciesGeneQuery_Tissues() {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics?geneQuery=blood&species=Homo%20sapiens");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");

        JsonPath json = response.jsonPath();

        assertThat((String)json.get("profiles.rows[0].id"), is("E-MTAB-2836"));
        assertThat((String)json.get("profiles.rows[1].id"), is("E-MTAB-1733"));
    }

    @Test
    public void cellLine() {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics?geneQuery=blood&species=Homo%20sapiens&source=CELL_LINE");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");

        JsonPath json = response.jsonPath();

        assertThat((String)json.get("profiles.rows[0].id"), is("E-GEOD-26284"));
        assertThat((String)json.get("profiles.rows[0].name"), is("ENCODE cell lines - long polyA RNA, whole cell"));
        assertThat(((Collection)json.get("profiles.rows[0].expressions")).size(), is(23));

        assertThat((String)json.get("profiles.rows[1].id"), is("E-GEOD-26284"));
        assertThat((String)json.get("profiles.rows[1].name"), is("ENCODE cell lines - long non-polyA RNA, whole cell"));
        assertThat(((Collection)json.get("profiles.rows[1].expressions")).size(), is(23));
    }

    @Test
    @Ignore //TODO: fix
    public void noExpressionsForGeneWithNoExpressions() {
        Response response = get("/widgets/heatmap/baselineAnalytics?geneQuery=OR4F4");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline expression found for OR4F4"));
    }

    @Test
    @Ignore //TODO: fix
    public void unknownExpressionResultsForLowDataGene() {
        Response response = get("/widgets/heatmap/baselineAnalytics?geneQuery=ENSG00000033100");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");

        JsonPath json = response.jsonPath();

        assertThat((String)json.get("profiles.rows[0].name"), is("CHPF2"));
        assertThat((String)json.get("profiles.rows[0].expressions[0].value"), is("UNKNOWN"));
    }

    @Test
    @Ignore //TODO: fix
    public void unknownGene() {
        Response response = get("/widgets/heatmap/baselineAnalytics?geneQuery=FOO");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No genes found matching query: FOO"));
    }

    @Test
    @Ignore //TODO: fix
    public void geneFromSpeciesWithoutbaselineAnalytics() {
        Response response = get("/widgets/heatmap/baselineAnalytics?geneQuery=RGD1304704");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline experiment for species rattus norvegicus"));
    }

    // tests the PAK-2p34:RHG10 link accessed from Reactome
    // ie: http://www.reactome.org/PathwayBrowser/#DIAGRAM=169911&ID=211701&PATH=5357801,109581&DTAB=EX
    @Test
    @Ignore //TODO: fix
    public void reactomeClient_multiGeneQuerySeparatedBySpaces() {

        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/baselineAnalytics?geneQuery=A1A4S6+Q13177");

        response.then().assertThat().statusCode(200);

        JsonPath json = response.jsonPath();

        //TODO: fix experiment URL
        assertThat((String) json.get("experiment.URL"), is("/experiments/E-MTAB-2836?geneQuery=A1A4S6 Q13177&serializedFilterFactors="));
    }

}
