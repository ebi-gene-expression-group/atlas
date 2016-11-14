
package uk.ac.ebi.atlas.experimentpage.experimentdesign;


import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MTAB1066ExperimentDesignDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-MTAB-1066/experiment-design.tsv");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -experiment-design.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-experiment-design.tsv"));
    }

    @Test
    public void verifyFirstLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> firstLine = subject.getRowValues(0);

        assertThat(firstLine,
                contains("Assay", "Array", "Sample Characteristic[developmental stage]", "Sample Characteristic Ontology Term[developmental stage]", "Sample Characteristic[genotype]", "Sample Characteristic Ontology Term[genotype]", "Sample Characteristic[organism]", "Sample Characteristic Ontology Term[organism]", "Sample Characteristic[strain]", "Sample Characteristic Ontology Term[strain]", "Factor Value[genotype]", "Factor Value Ontology Term[genotype]", "Analysed")
        );

    }

    @Test
    public void verifySecondLine() {
        ResponseBody body = subject.getResponseBody();

        List<String> secondLine = subject.getRowValues(1);

        assertThat(secondLine,
                contains("C1", "A-AFFY-35", "3rd instar larva", "", "w1118; +; cycCY5", "", "Drosophila melanogaster", "http://purl.obolibrary.org/obo/NCBITaxon_7227", "", "", "cycC mutant", "", "Yes")
        );

    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(10));
    }

}