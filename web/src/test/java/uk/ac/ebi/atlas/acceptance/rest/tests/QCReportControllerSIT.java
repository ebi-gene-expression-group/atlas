package uk.ac.ebi.atlas.acceptance.rest.tests;


import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class QCReportControllerSIT extends RestAssuredFixture {

    @Test
    public void qcReportMTAB1066(){
        Response response = get("/experiments/E-MTAB-1066/qc/A-AFFY-35/index.html");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("E-MTAB-1066"));
    }

    @Test
    public void qcReportMTAB1066_resources(){
        Response response = get("/experiments/E-MTAB-1066/qc/A-AFFY-35/box.png");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("image/png");
    }

}
