package uk.ac.ebi.atlas.bioentity;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class GenePageControllerMirBaseSIT extends RestAssuredFixture {

    @Test
    public void noResultForMirbaseHairpin() {
        Response response = get("/genes/hsa-mir-15a");
        response.then().assertThat().statusCode(404);
    }

    @Test
    public void noResultForMirbaseMature() {
        Response response = get("/genes/hsa-miR-486-5p");
        response.then().assertThat().statusCode(404);
    }

}
