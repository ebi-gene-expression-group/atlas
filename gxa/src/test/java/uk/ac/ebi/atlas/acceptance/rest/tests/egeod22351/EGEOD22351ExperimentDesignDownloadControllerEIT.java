
package uk.ac.ebi.atlas.acceptance.rest.tests.egeod22351;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EGEOD22351ExperimentDesignDownloadControllerEIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-22351/experiment-design.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-experiment-design.tsv"));
    }

    @Test
    public void verifyFirstLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("Run", "Sample Characteristic[age]", "Sample Characteristic Ontology Term[age]", "Sample Characteristic[genotype]", "Sample Characteristic Ontology Term[genotype]", "Sample Characteristic[organism]", "Sample Characteristic Ontology Term[organism]", "Sample Characteristic[organism part]", "Sample Characteristic Ontology Term[organism part]", "Sample Characteristic[strain]", "Sample Characteristic Ontology Term[strain]", "Factor Value[genotype]", "Factor Value Ontology Term[genotype]", "Analysed")
        );

    }

    @Test
    public void verifySecondLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> secondLine = subject.getRowValues(1);

        //TODO https://www.pivotaltracker.com/story/show/100371514
        //assertThat(secondLine,
        //        contains("SRR057596", "21 days", "", "non transgenic", "", "Mus musculus", "http://purl.obolibrary.org/obo/NCBITaxon_10090", "spinal cord", "http://purl.obolibrary.org/obo/UBERON_0002240", "C57BL/6;SJL", "", "non transgenic", "", "Yes")
        //);
        assertThat(secondLine,
                contains("SRR057596", "21 day", "", "non transgenic", "", "Mus musculus", "http://purl.obolibrary.org/obo/NCBITaxon_10090", "spinal cord", "http://purl.obolibrary.org/obo/UBERON_0002240", "C57BL/6;SJL", "", "non transgenic", "", "Yes")
        );

    }

    @Test
    public void verifyLengthOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(7));
    }

}
