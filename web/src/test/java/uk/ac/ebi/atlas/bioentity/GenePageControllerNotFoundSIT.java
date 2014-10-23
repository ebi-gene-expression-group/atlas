package uk.ac.ebi.atlas.bioentity;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class GenePageControllerNotFoundSIT extends RestAssuredFixture {

    @Test
    public void nonExistentGene() {
        Response response = get("/genes/FOOBAR");
        response.then().assertThat().statusCode(404);
    }

}
