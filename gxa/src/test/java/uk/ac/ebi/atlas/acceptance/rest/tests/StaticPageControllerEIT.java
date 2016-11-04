package uk.ac.ebi.atlas.acceptance.rest.tests;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class StaticPageControllerEIT extends RestAssuredFixture {

    @Test
    public void about() {
        Response response = get("/about.html");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.HTML);
        response.then().assertThat().body(containsString("About the Expression Atlas"));
    }

    @Test
    public void noPage() {
        Response response = get("/NOPAGE.html");
        response.then().assertThat().statusCode(404);
    }

    @Test
    public void help() {
        Response response = get("/help/index.html");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.HTML);
    }

}
