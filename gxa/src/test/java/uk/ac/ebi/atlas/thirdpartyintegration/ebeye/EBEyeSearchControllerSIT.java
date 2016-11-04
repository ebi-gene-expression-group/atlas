
package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.experiments.NumberOfExperiments;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

public class EBEyeSearchControllerSIT extends RestAssuredFixture {

    @Test
    public void experiments() {
        Response response = get("/api/experiments.xml");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.XML);
        response.then().assertThat().body("database.entry_count", equalTo(String.valueOf(NumberOfExperiments.ALL)));
        response.then().assertThat().body(hasXPath("/database/entries/entry[@id='E-GEOD-3307']/description", containsString("Comparative profiling in 13 muscle disease groups")));
    }
}