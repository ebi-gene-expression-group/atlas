package uk.ac.ebi.atlas.widget.multiexperiment;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class HeatmapWidgetControllerMultiExperimentSIT extends RestAssuredFixture {

    @Test
    public void noExpressionsForGeneWithNoExpressions() {
        Response response = get("/widgets/heatmap/multiExperiment?geneQuery=OR4F4");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline expression in tissues found for OR4F4"));
    }

    @Test
    public void noExpressionResultsForLowDataGene() {
        Response response = get("/widgets/heatmap/multiExperiment?geneQuery=AC004837.3");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No baseline expression in tissues found for AC004837.3"));
    }

    @Test
    public void unknownGene() {
        Response response = get("/widgets/heatmap/multiExperiment?geneQuery=FOO");

        response.then().assertThat().statusCode(404);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("No genes found matching query: FOO"));
    }


}
