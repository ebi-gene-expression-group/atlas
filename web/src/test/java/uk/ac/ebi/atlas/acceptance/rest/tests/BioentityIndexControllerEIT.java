
package uk.ac.ebi.atlas.acceptance.rest.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.is;

public class BioentityIndexControllerEIT extends RestAssuredAuthenticatedFixture {

    @Test
    public void statusShouldBeInitialized() {

        expect().statusCode(200).and().body(is("INITIALIZED")).when().get("buildIndex/status");

    }

}

