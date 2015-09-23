package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;

public class ContrastSummaryControllerSIT extends RestAssuredFixture {

    @Test
    public void contrastSummary() {
        Response response = get("/rest/contrast-summary?experimentAccession=E-GEOD-11758&contrastId=g1_g2&accessKey=");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);

        System.out.println(response.asString());

        // TODO https://www.pivotaltracker.com/story/show/100371514
        response.then().assertThat().body("properties[1]", allOf(
                // hasEntry("referenceValue", "20 degrees celsius"),
                hasEntry("referenceValue", "20 degree celsius"),
                hasEntry("propertyName", "temperature"),
                // hasEntry("testValue", "37 degrees celsius"),
                hasEntry("testValue", "37 degree celsius"),
                hasEntry("contrastPropertyType", "FACTOR")
        ));

        response.then().assertThat().body("properties[6]", allOf(
                hasEntry("referenceValue", "leaf"),
                hasEntry("propertyName", "organism part"),
                hasEntry("testValue", "leaf"),
                hasEntry("contrastPropertyType", "SAMPLE")
        ));

        response.then().assertThat().body("testReplicates", is(3));
        response.then().assertThat().body("referenceReplicates", is(3));

    }
}