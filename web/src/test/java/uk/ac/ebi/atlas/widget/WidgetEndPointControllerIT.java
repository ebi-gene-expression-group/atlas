package uk.ac.ebi.atlas.widget;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class WidgetEndPointControllerIT extends RestAssuredFixture {

    @Test
    public void existExpressionForGeneIdentifier() {
        Response response = get("/json/expressionData?geneId=ENSMUSG00000021789");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString("\"ENSMUSG00000021789\":true"));
    }

    @Test
    public void noExpressionForGeneIdentifier() {
        Response response = get("/json/expressionData?geneId=ENSG00000164399");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString("\"ENSG00000164399\":false"));
    }
}