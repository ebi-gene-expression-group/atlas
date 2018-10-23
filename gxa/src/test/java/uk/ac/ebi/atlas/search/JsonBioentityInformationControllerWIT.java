package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.SolrUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class JsonBioentityInformationControllerWIT {
    @Autowired
    private WebApplicationContext wac;

    @Inject
    private SolrUtils solrUtils;

    private MockMvc mockMvc;

    private static final String urlTemplate = "/json/bioentity_information/{geneId}";

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void payloadIsValidJson() throws Exception {
        String geneId = solrUtils.fetchRandomGeneIdFromAnalytics();

        this.mockMvc
                .perform(get(urlTemplate, geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].type", isA(String.class)))
                .andExpect(jsonPath("$[0].name", isA(String.class)))
                .andExpect(jsonPath("$[0].values", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].values[0].text", isA(String.class)))
                .andExpect(jsonPath("$[0].values[0].url", isA(String.class)))
                .andExpect(jsonPath("$[0].values[0].relevance", isA(Number.class)));
    }

    @Test
    public void geneIdContainingDotIsNotTruncated() throws Exception {
        String geneId = solrUtils.fetchRandomGeneOfSpecies("Arabidopsis_lyrata"); // has gene IDs containing dots

        if(!geneId.isEmpty()) {
            this.mockMvc
                    .perform(get(urlTemplate, geneId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        }
    }

    @Test
    public void geneNotFound() throws Exception {
        this.mockMvc
                .perform(get(urlTemplate ,"unknown"))
                .andExpect(status().isNotFound());
    }
}
