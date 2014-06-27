package uk.ac.ebi.atlas.search.EFO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EFOTermExpansionClientTest {

    @Mock
    private RestTemplate restTemplate;

    private EFOTermExpansionClient subject = new EFOTermExpansionClient(restTemplate, "");

    @Test
    public void testExtractIds() throws Exception {
        String jsonString = "{\n" +
                "  \"tree\": [\n" +
                "    {\n" +
                "      \"id\": \"EFO_0000304\",\n" +
                "      \"count\": 0,\n" +
                "      \"root\": false,\n" +
                "      \"term\": \"breast adenocarcinoma\",\n" +
                "      \"depth\": 0,\n" +
                "      \"alternativeTerms\": [],\n" +
                "      \"expandable\": false,\n" +
                "      \"branchRoot\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"EFO_0001416\",\n" +
                "      \"count\": 0,\n" +
                "      \"root\": false,\n" +
                "      \"term\": \"cervical adenocarcinoma\",\n" +
                "      \"depth\": 0,\n" +
                "      \"alternativeTerms\": [],\n" +
                "      \"expandable\": false,\n" +
                "      \"branchRoot\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        assertThat(subject.extractIds(jsonString), contains("EFO:0000304", "EFO:0001416"));
    }
}
