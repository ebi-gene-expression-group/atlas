package uk.ac.ebi.atlas.search.diffanalytics;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class BioentitiesSearchDifferentialDownloadControllerSIT {

    private EndPoint subject = new EndPoint("/gxa/query.tsv?geneQuery=Cyba&exactMatch=true&_exactMatch=on&organism=Any&condition=");

    @Test
    public void verifyLengthOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(7));
    }

    @Test
    public void verifyHeader() {
        List<String> header = subject.getRowValues(3);

        assertThat(header,
                contains("Gene", "Organism", "Experiment Accession", "Comparison", "p-value", "log2foldchange", "t-statistic")
        );

    }

    @Test
    public void verifyRnaSeqGene() {
        List<String> firstGene = subject.getRowValues(5);

        assertThat(firstGene,
                contains("Cyba\tMus musculus\tE-MEXP-1276\tcompound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36\t4.12746725021875E-5\t1.35626816666667\t14.8773555318722".split("\t")));
    }

    @Test
    public void verifyMicroArrayGene() {
        List<String> firstGene = subject.getRowValues(4);

        assertThat(firstGene,
                contains("CYBA\tHomo sapiens\tE-GEOD-12108\t'Francisella tularensis Schu S4' vs 'uninfected'\t7.29020235742829E-4\t-2.153295\t-6.11401975251878".split("\t")));
    }



    @Test
    public void downloadGeneDifferentialExpressions() {
        Response response = new EndPoint("/gxa/genes/ENSMUSG00000050520.tsv").getResponse();

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));
    }


    @Test
    public void downloadGeneQueryWithOrganism() {
        String url = "/gxa/query.tsv?geneQuery=Cyba&exactMatch=true&_exactMatch=on&organism=Homo%20sapiens&condition=";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("CYBA\tHomo sapiens\tE-GEOD-12108\t'Francisella tularensis Schu S4' vs 'uninfected'\t7.29020235742829E-4\t-2.153295\t-6.11401975251878"));
    }


    @Test
    public void downloadConditionWithOrganism() {
        String url = "/gxa/query.tsv?geneQuery=&exactMatch=true&_exactMatch=on&organism=Arabidopsis+thaliana&condition=%22wild+type%22";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(54));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("AT3G48131\tArabidopsis thaliana\tE-GEOD-38400\tnrpe1 mutant vs wild type\t4.2479998366313E-5\t6.89620951324986\tNA"));
    }

}
