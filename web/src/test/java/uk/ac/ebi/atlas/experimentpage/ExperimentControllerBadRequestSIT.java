package uk.ac.ebi.atlas.experimentpage;

import com.jayway.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;

public class ExperimentControllerBadRequestSIT extends RestAssuredFixture {

    @Test
    public void baseline_geneQueryWithInvalidSyntax_GeneratesMessageWithSolrSyntaxError(){
        String url = "/experiments/E-MTAB-599?geneQuery=foo\"bar\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
        response.then().assertThat().body(containsString("org.apache.solr.search.SyntaxError"));
    }

    @Test
    public void differential_geneQueryWithInvalidSyntax_GeneratesMessageWithSolrSyntaxError(){
        String url = "/experiments/E-TABM-51?geneQuery=foo\"bar\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
        response.then().assertThat().body(containsString("org.apache.solr.search.SyntaxError"));
    }

    @Test
    public void baseline_geneIdWithUnmatchedQuote(){
        String url = "/experiments/E-MTAB-599?geneQuery=foo\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);

    }

    @Test
    public void differential_geneIdWithUnmatchedQuote(){
        String url = "/experiments/E-TABM-51?geneQuery=foo\"";

        Response response = get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);
    }

}
