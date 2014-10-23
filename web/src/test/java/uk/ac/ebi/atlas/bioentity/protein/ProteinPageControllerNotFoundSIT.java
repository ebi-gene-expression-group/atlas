package uk.ac.ebi.atlas.bioentity.protein;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class ProteinPageControllerNotFoundSIT extends RestAssuredFixture {

    @Test
    public void nonExistentProtein() {
        Response response = get("/proteins/FOOBAR");
        response.then().assertThat().statusCode(404);
    }

}
