package uk.ac.ebi.atlas.bioentity.geneset;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class GeneSetPageControllerNotFoundSIT extends RestAssuredFixture {

    @Test
    public void notAGeneSetId(){
        Response response = get("/genesets/FOOBAR");

        response.then().assertThat().statusCode(404);
    }

    @Test
    public void geneSetNotInSolr(){
        Response response = get("/genesets/REACT_FOO");

        response.then().assertThat().statusCode(404);
    }
}
