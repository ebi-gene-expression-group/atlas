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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static uk.ac.ebi.atlas.utils.GeneSetEnrichmentClient.GSA_URL_PATTERN;

@RunWith(MockitoJUnitRunner.class)
public class GeneSetEnrichmentClientTest {

    private MockRestServiceServer mockServer;

    private GeneSetEnrichmentClient subject;

    private static final String ARABIDOPSIS_SPECIES = "arabidopsis_thaliana";
    private static final String GENE_IDS =
            "AT1G48030 AT1G53240 AT2G17130 AT2G20420 AT2G44350 AT2G47510 AT3G15020 AT3G17240 AT3G27380 AT3G55410 " +
            "AT3G60100 AT4G26910";
    private static final String SUCCESS_TSV =
            "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\t" +
                    "COMPARISON_TITLE\tEXPERIMENT_URL\n" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\t4.55\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n" +
            "EXP_02\tg3_g4\t0.1\t5\t2\t0.005\t3.2\t'g3 vs g4'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_02\n";
    private static final String TSV_WITH_BAD_HEADER =
            "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\t" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\t4.55\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n";
    private static final String TSV_WITH_NAN =
            "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE" +
                    "\tCOMPARISON_TITLE\tEXPERIMENT_URL\n" +
            "EXP_01\tg1_g2\t1.55\t10\t2.2\t0.01\tInf\t'g1 vs g2'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_01\n" +
            "EXP_02\tg3_g4\t0.1\t5\t2\t0.005\t3.2\t'g3 vs g4'\thttp://www.ebi.ac.uk/gxa/experiments/GENE_02\n";
    private static final String TSV_WITH_DIFFERENT_LINE_SIZES =
            "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\t" +
                    "COMPARISON_TITLE\tEXPERIMENT_URL\n" +
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
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(SUCCESS_TSV, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getLeft()).isEmpty();
        assertThat(result.getRight()).isPresent();

        JsonArray jsonArray = result.getRight().orElseThrow(RuntimeException::new);
        assertThat(jsonArray).hasSize(2);

        assertThat(jsonArray.get(0).getAsJsonObject().get("experiment_accession").getAsString()).isEqualTo("EXP_01");
        assertThat(jsonArray.get(0).getAsJsonObject().get("comparison_id").getAsString()).isEqualTo("g1_g2");
        assertThat(jsonArray.get(0).getAsJsonObject().get("p-value").getAsDouble()).isEqualTo(1.55);
        assertThat(jsonArray.get(0).getAsJsonObject().get("observed").getAsInt()).isEqualTo(10);
        assertThat(jsonArray.get(0).getAsJsonObject().get("adjusted_p-value").getAsDouble()).isEqualTo(0.01);
        assertThat(jsonArray.get(0).getAsJsonObject().get("effect_size").getAsDouble()).isEqualTo(4.55);
        assertThat(jsonArray.get(0).getAsJsonObject().get("comparison_title").getAsJsonObject().entrySet()).isEmpty();
        assertThat(jsonArray.get(0).getAsJsonObject().get("experiment").getAsString()).isEmpty();

        assertThat(jsonArray.get(1).getAsJsonObject().get("experiment_accession").getAsString()).isEqualTo("EXP_02");
        assertThat(jsonArray.get(1).getAsJsonObject().get("comparison_id").getAsString()).isEqualTo("g3_g4");
        assertThat(jsonArray.get(1).getAsJsonObject().get("p-value").getAsDouble()).isEqualTo(0.1);
        assertThat(jsonArray.get(1).getAsJsonObject().get("observed").getAsInt()).isEqualTo(5);
        assertThat(jsonArray.get(1).getAsJsonObject().get("adjusted_p-value").getAsDouble()).isEqualTo(0.005);
        assertThat(jsonArray.get(1).getAsJsonObject().get("effect_size").getAsDouble()).isEqualTo(3.2);
        assertThat(jsonArray.get(1).getAsJsonObject().get("comparison_title").getAsJsonObject().entrySet()).isEmpty();
        assertThat(jsonArray.get(1).getAsJsonObject().get("experiment").getAsString()).isEmpty();
    }

    @Test
    public void testNoSignificantResult() {
        String geneIdsSubset = "AT1G48030 AT1G53240 AT2G17130";
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, geneIdsSubset)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        List<String> bioentityIdentifiers = Arrays.asList(geneIdsSubset.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight()).isEmpty();
        assertThat(result.getLeft()).isPresent();
    }

    @Test
    public void testEmptyResult() {
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight()).isEmpty();
        assertThat(result.getLeft()).isPresent();
    }

    @Test
    public void testMalformedTsvHeaderResult() {
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_BAD_HEADER, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight()).isEmpty();
        assertThat(result.getLeft()).isPresent();
    }

    @Test
    public void testMalformedTsvFileResult() {
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_DIFFERENT_LINE_SIZES, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getRight()).isEmpty();
        assertThat(result.getLeft()).isPresent();
    }

    @Test
    public void testNaNValuesResult() {
        String url =
                UriComponentsBuilder
                        .fromUriString(GSA_URL_PATTERN)
                        .buildAndExpand(ARABIDOPSIS_SPECIES, GENE_IDS)
                        .encode()
                        .toUriString();
        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(TSV_WITH_NAN, MediaType.TEXT_PLAIN));

        List<String> bioentityIdentifiers = Arrays.asList(GENE_IDS.split("\\W+"));
        Pair<Optional<Exception>, Optional<JsonArray>> result =
                subject.fetchEnrichedGenes(ARABIDOPSIS_SPECIES, bioentityIdentifiers);

        assertThat(result.getLeft()).isEmpty();
        assertThat(result.getRight()).isPresent();

        JsonArray jsonArray = result.getRight().orElseThrow(RuntimeException::new);
        assertThat(jsonArray).hasSize(2);

        assertThat(jsonArray.get(0).getAsJsonObject().get("effect_size").getAsString()).isEqualTo("NaN");
        assertThat(jsonArray.get(1).getAsJsonObject().get("effect_size").getAsDouble()).isEqualTo(3.2);
    }
}
