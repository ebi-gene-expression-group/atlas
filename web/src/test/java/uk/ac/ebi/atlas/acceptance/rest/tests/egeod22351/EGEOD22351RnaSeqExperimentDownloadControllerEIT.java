package uk.ac.ebi.atlas.acceptance.rest.tests.egeod22351;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EGEOD22351RnaSeqExperimentDownloadControllerEIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-22351.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-query-results.tsv"));
    }

    @Test
    public void verifyProjectVersionHeader() {

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("# Expression Atlas version: 3.0")
        );

    }

    @Test
    public void verifyHeaders() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "'expressing human TDP-43' vs 'non transgenic'.p-value", "'expressing human TDP-43' vs 'non transgenic'.log2foldchange")
        );

    }

    @Test
    public void verifyLines() {

        List<String> firstLine = subject.getRowValues(4);
        assertThat(firstLine.get(0), startsWith("ENSMUSG"));
        assertThat(firstLine, hasSize(4));

        List<String> secondLine = subject.getRowValues(5);
        assertThat(secondLine.get(0), startsWith("ENSMUSG"));
        assertThat(secondLine, hasSize(4));

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(greaterThan(4)));   // Has at least one result
    }

}
