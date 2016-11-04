package uk.ac.ebi.atlas.experimentpage.tooltip;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;

public class AssayGroupSummaryControllerSIT extends RestAssuredFixture {

    @Test
    public void assayGroupSummary() {
        Response response = get("/rest/assayGroup-summary?experimentAccession=E-MTAB-513&assayGroupId=g15&accessKey=");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);

        System.out.println(response.asString());

//        response.then().assertThat().body("properties", allOf( hasEntry("testValue","adipose tissue")
//                hasEntry("propertyName", "organism part"),
//                hasEntry("testValue", "adipose tissue"),
//                hasEntry("contrastPropertyType", "FACTOR")
//        ));

//        response.then().assertThat().body("properties[4]", allOf(
//                hasEntry("propertyName", "sex"),
//                hasEntry("testValue", "female"),
//                hasEntry("contrastPropertyType", "SAMPLE")
//        ));

        response.then().assertThat().body("replicates", is(1));
    }
}