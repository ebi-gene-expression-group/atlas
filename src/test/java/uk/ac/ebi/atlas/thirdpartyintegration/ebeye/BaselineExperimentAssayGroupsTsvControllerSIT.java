package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class BaselineExperimentAssayGroupsTsvControllerSIT extends RestAssuredFixture {

    @Test
    public void assayGroups(){
        Response response = get("/api/assaygroupsdetails.tsv");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().body(containsString("E-GEOD-41338"));
        response.then().assertThat().body(containsString("g9"));
        response.then().assertThat().body(containsString("http://purl.obolibrary.org/obo/UBERON_0000948"));
    }
}
