package uk.ac.ebi.atlas.acceptance.rest.tests;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class GenePageControllerIT extends RestAssuredFixture {

    @Test
    public void genePageRedirectQueryController(){
        Response response = get("/query?geneQuery=MGI:3A108498");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
    }

}
