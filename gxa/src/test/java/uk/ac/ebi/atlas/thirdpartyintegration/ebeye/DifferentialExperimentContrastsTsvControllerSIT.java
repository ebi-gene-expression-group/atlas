package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class DifferentialExperimentContrastsTsvControllerSIT extends RestAssuredFixture {

    @Test
    public void contrastLinesExperiments(){
        Response response = get("/api/contrastdetails.tsv");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/tab-separated-values");
        response.then().assertThat().body(containsString("E-MTAB-698"));
        response.then().assertThat().body(containsString("vomeronasal organ"));
        response.then().assertThat().body(containsString("http://www.ebi.ac.uk/efo/EFO_0001265"));
    }
}
