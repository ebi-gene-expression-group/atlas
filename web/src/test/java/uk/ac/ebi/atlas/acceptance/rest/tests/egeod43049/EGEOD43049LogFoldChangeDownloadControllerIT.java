package uk.ac.ebi.atlas.acceptance.rest.tests.egeod43049;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EGEOD43049LogFoldChangeDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-43049/logFold.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-log-fold-changes.tsv"));
    }

    @Test
    public void verifyFirstLine() {
        List<String> firstLine = subject.getRowValues(0);
        assertThat(firstLine, hasSize(9));
    }

    @Test
    public void verifySecondLine() {
        List<String> secondLine = subject.getRowValues(2);
        assertThat(secondLine, hasSize(9));
    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(greaterThan(1)));   // Has at least one line
    }

}