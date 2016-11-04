package uk.ac.ebi.atlas.widget.referenceexperiment;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
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


}
