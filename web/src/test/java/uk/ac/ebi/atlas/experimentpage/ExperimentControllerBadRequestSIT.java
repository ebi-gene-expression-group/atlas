package uk.ac.ebi.atlas.experimentpage;

import com.jayway.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;

public class ExperimentControllerBadRequestSIT extends RestAssuredFixture {

    @Test
    public void baseline_geneIdWithUnmatchedQuote_GeneratesMessageWithSolrSyntaxError(){
        String url = "/experiments/E-MTAB-599?geneQuery=foo\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
        response.then().assertThat().body(containsString("org.apache.solr.search.SyntaxError"));
    }

    @Test
    public void differential_geneIdWithUnmatchedQuote_GeneratesMessageWithSolrSyntaxError(){
        String url = "/experiments/E-TABM-51?geneQuery=foo\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
        response.then().assertThat().body(containsString("org.apache.solr.search.SyntaxError"));
    }

}
