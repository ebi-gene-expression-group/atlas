package uk.ac.ebi.atlas.utils;

import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static uk.ac.ebi.atlas.utils.GeneSetEnrichmentClient.urlPattern;

@RunWith(MockitoJUnitRunner.class)
public class GeneSetEnrichmentClientTest {

    private MockRestServiceServer mockServer;

    private GeneSetEnrichmentClient subject;

    private final static String ARABIDOPSIS_SPECIES = "arabidopsis_thaliana";
    private final static String GENE_IDS = "AT1G48030 AT1G53240 AT2G17130 AT2G20420 AT2G44350 AT2G47510 AT3G15020 AT3G17240 AT3G27380 AT3G55410 AT3G60100 AT4G26910";
    private final static String SUCCESS_TSV = "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL\n" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\t4.55\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n" +
            "EXP_02\tg3_g4\t0.1\t5\t2\t0.005\t3.2\t'g3 vs g4'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_02\n";
    private final static String TSV_WITH_BAD_HEADER = "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\t"+
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\t4.55\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n";
    private final static String TSV_WITH_NAN = "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL\n" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\tInf\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n" +
            "EXP_02\tg3_g4\t0.1\t5\t2\t0.005\t3.2\t'g3 vs g4'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_02\n";
    private final static String TSV_WITH_DIFFERENT_LINE_SIZES = "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL\n" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\t4.55\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n" +
            "EXP_02\tg3_g4\t0.1\t5\t2\t0.005\t3.2";

    @Before
    public void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        subject = new GeneSetEnrichmentClient(restTemplate);
    }

    @Test
    public void testSuccessWith2Results() {
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS).encode().toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        SUCCESS_TSV,
                        MediaType.TEXT_PLAIN
                ));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(jsonPath("$", hasSize(2)));
        assertThat(jsonPath("$[0].experiment_accession", is("EXP_01")));
        assertThat(jsonPath("$[0].comparison_id", is("g1_g2")));
        assertThat(jsonPath("$[0].p-value", is(1.55)));
        assertThat(jsonPath("$[0].observed", is(10)));
        assertThat(jsonPath("$[0].adjusted_p-value", is(0.01)));
        assertThat(jsonPath("$[0].effect_size", is(4.55)));
        assertThat(jsonPath("$[0].comparison_title", isEmptyOrNullString()));
        assertThat(jsonPath("$[0].experiment", isEmptyOrNullString()));

        assertThat(jsonPath("$[1].experiment_accession", is("EXP_02")));
        assertThat(jsonPath("$[1].comparison_id", is("g3_g4")));
        assertThat(jsonPath("$[1].p-value", is(0.1)));
        assertThat(jsonPath("$[1].observed", is(5)));
        assertThat(jsonPath("$[1].adjusted_p-value", is(0.005)));
        assertThat(jsonPath("$[1].effect_size", is(3.2)));
        assertThat(jsonPath("$[1].comparison_title", isEmptyOrNullString()));
        assertThat(jsonPath("$[1].experiment", isEmptyOrNullString()));

        assertThat(result.getLeft().equals(Optional.empty()));
    }

    @Test
    public void testNoSignificantResult() {
        String geneIdsSubset = "AT1G48030 AT1G53240 AT2G17130";
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, geneIdsSubset).encode().toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        List<String> bioentityIdentifiers = Arrays.asList(geneIdsSubset.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight().equals(Optional.empty()));
        assertThat(result.getLeft().isPresent());
    }

    @Test
    public void testEmptyResult() {
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS).encode().toUriString();

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight().equals(Optional.empty()));
        assertThat(result.getLeft().isPresent());
    }

    @Test
    public void testMalformedTsvHeaderResult() {
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS).encode().toUriString();

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_BAD_HEADER, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight().equals(Optional.empty()));
        assertThat(result.getLeft().isPresent());
    }

    @Test
    public void testMalformedTsvFileResult() {
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS).encode().toUriString();

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_DIFFERENT_LINE_SIZES, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight().equals(Optional.empty()));
        assertThat(result.getLeft().isPresent());
    }

    @Test
    public void testNaNValuesResult() {
        String url = UriComponentsBuilder.fromUriString(urlPattern).buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS).encode().toUriString();

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_NAN, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result = subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);
        
        assertThat(jsonPath("$", hasSize(2)));
        assertThat(jsonPath("$[0].effect_size", is("NaN")));
        assertThat(jsonPath("$[1].effect_size", is(3.2)));
    }
}