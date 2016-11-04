package uk.ac.ebi.atlas.experimentpage.baseline.download;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProteomicsBaselineExperimentDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-PROT-1.tsv?geneQuery=");

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
                contains("Gene ID", "Gene Name", "B cell", "CD4-positive T cell", "CD8-positive T cell", "adrenal gland", "colon", "esophagus", "frontal cortex", "gallbladder",
                        "heart", "kidney", "liver", "lung", "monocyte", "natural killer cell", "ovary", "pancreas", "platelet", "prostate", "rectum", "retina", "spinal cord", "testis", "urinary bladder"));

        assertThat(firstLine.size(), is(25));
    }


    @Test
    public void verifySecondLine() {

        List<String> secondLine = subject.getRowValues(4);

        assertThat(secondLine, hasItems("ENSG00000000003","TSPAN6"));
        assertThat(secondLine.get(3), isEmptyString());
    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(195));
    }


}