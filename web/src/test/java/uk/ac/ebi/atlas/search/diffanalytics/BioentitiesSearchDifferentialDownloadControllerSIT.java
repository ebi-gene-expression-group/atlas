package uk.ac.ebi.atlas.search.diffanalytics;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class BioentitiesSearchDifferentialDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/query.tsv?geneQuery=AT1G02220");

    @Test
    public void verifyLengthOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(6));
    }

    @Test
    public void verifyHeader() {
        List<String> header = subject.getRowValues(3);

        assertThat(header,
                contains("Gene", "Design Element", "Organism", "Contrast", "p-value", "log2foldchange", "t-statistic")
        );

    }

    @Test
    public void verifyBaselineGene() {
        List<String> firstGene = subject.getRowValues(4);

        assertThat(firstGene,
                contains("ANAC003", "", "Arabidopsis thaliana","nrpe1 mutant vs wild type",	"0.00355665145244311", "2.17880566798814", "NA"));
    }

    @Test
    public void verifyMicroArrayGene() {
        List<String> firstGene = subject.getRowValues(5);

        assertThat(firstGene,
                contains("ANAC003", "264148_at", "Arabidopsis thaliana","treatment: 'salicylic acid' vs 'Silwet' at time: '4 hours' in ecotype: 'Col-0'",	"0.0476008286349805", "0.982933333333333", "4.81369176288206"));
    }



    @Test
    public void downloadGeneDifferentialExpressions() {
        Response response = new EndPoint("/gxa/genes/ENSMUSG00000050520.tsv").getResponse();

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));
    }

}
