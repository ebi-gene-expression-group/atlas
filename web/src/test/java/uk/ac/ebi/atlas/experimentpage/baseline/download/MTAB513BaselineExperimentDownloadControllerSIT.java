
package uk.ac.ebi.atlas.experimentpage.baseline.download;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MTAB513BaselineExperimentDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-MTAB-513.tsv?geneQuery=&exactMatch=false");

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
    public void verifyFirstLine() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "adipose tissue", "adrenal gland", "animal ovary", "brain", "breast", "colon", "heart", "kidney", "leukocyte", "liver", "lung", "lymph node", "prostate", "skeletal muscle", "testis", "thyroid")
        );

    }

    @Test
    public void verifySecondLine() {

        List<String> secondLine = subject.getRowValues(4);

        assertThat(secondLine,
                hasItems("ENSG00000244656", "ENSG00000244656", "57")
        );

        assertThat(secondLine.get(2), isEmptyString());
        assertThat(secondLine.get(3), isEmptyString());
        assertThat(secondLine.get(4), isEmptyString());
        assertThat(secondLine.get(5), isEmptyString());
        assertThat(secondLine.get(6), isEmptyString());
        assertThat(secondLine.get(7), isEmptyString());
        assertThat(secondLine.get(9), isEmptyString());
        assertThat(secondLine.get(10), isEmptyString());
        assertThat(secondLine.get(11), isEmptyString());
        assertThat(secondLine.get(12), isEmptyString());
        assertThat(secondLine.get(13), isEmptyString());
        assertThat(secondLine.get(14), isEmptyString());
        assertThat(secondLine.get(15), isEmptyString());
        assertThat(secondLine.get(16), isEmptyString());
        assertThat(secondLine.get(17), isEmptyString());

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(267));
    }

}