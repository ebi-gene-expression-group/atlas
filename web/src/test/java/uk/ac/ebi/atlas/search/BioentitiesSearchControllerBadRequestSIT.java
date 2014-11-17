package uk.ac.ebi.atlas.search;

import com.jayway.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;

public class BioentitiesSearchControllerBadRequestSIT extends RestAssuredFixture {

    @Test
    public void geneIdWithUnmatchedQuote_GeneratesMessageWithSolrSyntaxError(){
        String url = "/query?geneQuery=ENSG00000161547\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
        response.then().assertThat().body(containsString("org.apache.solr.search.SyntaxError"));
    }

}
