package uk.ac.ebi.atlas.acceptance.rest.tests.DAS;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class DASStaticResourcesEIT {

    public static final int HTTP_OK = 200;

    @Test
    public void DASS4URLReturnsXML() {
        EndPoint subject = new EndPoint("/gxa/das/s4");
        Response response = subject.getResponse();

        assertThat(response.getStatusCode(), is(HTTP_OK));
        assertThat(response.getContentType(), is("application/xml"));
        assertThat(response.getBody().asString(), not(isEmptyOrNullString()));
    }

    @Test
    public void DASS4TypesURLReturnsXML() {
        EndPoint subject = new EndPoint("/gxa/das/s4/types");
        Response response = subject.getResponse();

        assertThat(response.getStatusCode(), is(HTTP_OK));
        assertThat(response.getContentType(), is("application/xml"));
        assertThat(response.getBody().asString(), not(isEmptyOrNullString()));
    }

}
