package uk.ac.ebi.atlas.widget.referenceexperiment;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class HeatmapWidgetControllerReferenceExperimentSIT extends RestAssuredFixture {

    @Test
    public void noExpressionsForGeneWithNoExpressions() {
        Response response = get("/widgets/heatmap/referenceExperiment?geneQuery=OR4F4");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline expression found for OR4F4"));
    }

    @Test
    public void unknownExpressionResultsForLowDataGene() {
        Response response = get("/widgets/heatmap/referenceExperiment?geneQuery=ENSG00000033100");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");

        JsonPath json = response.jsonPath();

        assertThat((String)json.get("profiles.rows[0].name"), is("CHPF2"));
        assertThat((String)json.get("profiles.rows[0].expressions[0].value"), is("UNKNOWN"));
    }

    @Test
    public void unknownGene() {
        Response response = get("/widgets/heatmap/referenceExperiment?geneQuery=FOO");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No genes found matching query: FOO"));
    }

    @Test
    public void geneFromSpeciesWithoutReferenceExperiment() {
        Response response = get("/widgets/heatmap/referenceExperiment?geneQuery=RGD1304704");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline experiment for species rattus norvegicus"));
    }

    // tests the PAK-2p34:RHG10 link accessed from Reactome
    // ie: http://www.reactome.org/PathwayBrowser/#DIAGRAM=169911&ID=211701&PATH=5357801,109581&DTAB=EX
    @Test
    public void reactomeClient_multiGeneQuerySeparatedBySpaces() {

        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/referenceExperiment?geneQuery=A1A4S6+Q13177");

        response.then().assertThat().statusCode(200);

        JsonPath json = response.jsonPath();

        //TODO: fix experiment URL
        assertThat((String) json.get("experiment.URL"), is("/experiments/E-MTAB-2836?geneQuery=A1A4S6 Q13177&serializedFilterFactors="));
    }

}
