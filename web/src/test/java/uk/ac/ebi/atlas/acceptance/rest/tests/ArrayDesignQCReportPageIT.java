package uk.ac.ebi.atlas.acceptance.rest.tests;


import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class ArrayDesignQCReportPageIT extends RestAssuredFixture {

    @Test
    public void arrayDesignReportMTAB1066(){
        Response response = get("/resources/html/qc/E-MTAB-1066_A-AFFY-35_QM/index.html");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("E-MTAB-1066"));
    }

}
