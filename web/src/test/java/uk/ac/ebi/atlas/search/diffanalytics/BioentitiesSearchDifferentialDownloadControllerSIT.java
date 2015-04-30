package uk.ac.ebi.atlas.search.diffanalytics;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchDifferentialDownloadControllerSIT extends RestAssuredFixture {

    private EndPoint subject = new EndPoint("/gxa/query.tsv?geneQuery=Cyba&exactMatch=true&_exactMatch=on&organism=Any&condition=");

    @Test
    public void hasResultsForEfoAnnotatedContrasts() {
        Response response = get("query.tsv?geneQuery=&exactMatch=true&_exactMatch=on&organism=Any&condition=sex");

        ResponseBody body = response.getBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(greaterThan(4)));
        assertThat(lines[4], startsWith("Cp36\tDrosophila melanogaster\tE-MEXP-1099\t'homozygous for chico mutation' vs 'wild type'"));
    }

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
        Response response = new EndPoint("/gxa/genes/ENSMUSG00000083856.tsv").getResponse();

        response.then().assertThat().statusCode(200).contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));
    }


    @Test
    public void downloadSearchGeneQueryWithOrganism() {
        String url = "/query.tsv?geneQuery=Cyba&exactMatch=true&_exactMatch=on&organism=Homo%20sapiens&condition=";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("CYBA\tHomo sapiens\tE-GEOD-12108\t'Francisella tularensis Schu S4' vs 'uninfected'\t7.29020235742829E-4\t-2.153295\t-6.11401975251878"));
    }


    @Test
    public void downloadSearchConditionWithOrganism() {
        String url = "/query.tsv?geneQuery=&exactMatch=true&_exactMatch=on&organism=Arabidopsis+thaliana&condition=%22wild+type%22";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(200).contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(54));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("AT3G48131\tArabidopsis thaliana\tE-GEOD-38400\tnrpe1 mutant vs wild type\t4.2479998366313E-5\t6.89620951324986\tNA"));
    }

    @Test
    public void downloadGene() {
        String url = "/genes/ENSG00000026103.tsv";

        Response response = given().urlEncodingEnabled(false).log().all().get(url);

        response.then().assertThat().statusCode(200).contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("FAS\tHomo sapiens\tE-GEOD-12108\t'Francisella tularensis novicida' vs 'uninfected'\t0.00141996014524383\t3.02983011666667\t6.21178806331197"));
    }


    @Test
    public void downloadGeneSet() {
        String url = "/genesets/REACT_1619.tsv";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(200).contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(15));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("FAS\tHomo sapiens\tE-GEOD-12108\t'Francisella tularensis novicida' vs 'uninfected'\t0.00141996014524383\t3.02983011666667\t6.21178806331197"));
    }


    @Test
    public void downloadWithLogFoldChangeOfInfinity() {
        String url = "/genes/ENSMUSG00000069045.tsv";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(200).contentType(ContentType.TEXT);

        String[] lines = response.body().asString().split("\n");
        assertThat(lines.length, is(5));

        String firstGene = lines[4];
        assertThat(firstGene,
                is("Ddx3y\tMus musculus\tE-MTAB-698\tsex:'male' vs 'female'\t3.78366892203992E-123\tInf\tNA"));
    }
}
