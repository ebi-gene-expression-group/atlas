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

    private EndPoint subject = new EndPoint("/gxa/query.tsv?geneQuery=Cyba");

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
                contains("Gene", "Organism", "Experiment Accession", "Contrast", "p-value", "log2foldchange", "t-statistic")
        );

    }

    @Test
    public void verifyRnaSeqGene() {
        List<String> firstGene = subject.getRowValues(5);

        assertThat(firstGene,
                contains("Cyba\tMus musculus\tE-GEOD-22351\tgenotype:'expressing human TDP-43' vs 'non transgenic'\t0.0206187013724948\t1.33594422753461\tNA".split("\t")));
    }

    @Test
    public void verifyMicroArrayGene() {
        List<String> firstGene = subject.getRowValues(4);

        assertThat(firstGene,
                contains("Cyba\tMus musculus\tE-MEXP-1276\tcompound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36\t4.12746725021875E-5\t1.35626816666667\t14.8773555318722".split("\t")));
    }



    @Test
    public void downloadGeneDifferentialExpressions() {
        Response response = new EndPoint("/gxa/genes/ENSMUSG00000050520.tsv").getResponse();

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));
    }

}
