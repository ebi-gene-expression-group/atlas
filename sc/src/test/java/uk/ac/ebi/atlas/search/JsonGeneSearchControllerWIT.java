package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JsonGeneSearchControllerWIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validJsonForInvalidGeneId() throws Exception {
        this.mockMvc.perform(get("/json/search/FOO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results").isEmpty());
    }

    @Test
    public void validJsonForValidGeneId() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc.perform(get("/json/search/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].element.experimentAccession", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].facets[0].group", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].value", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].label", isA(String.class)))
                .andExpect(jsonPath("$.checkboxFacetGroups", contains("Marker genes", "Species")));
    }

    @Test
    public void jsonPayloadContainsFacetDescription() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc.perform(get("/json/search/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].element.experimentAccession", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].facets[0].group", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].value", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].label", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].description", isA(String.class)))
                .andExpect(jsonPath("$.checkboxFacetGroups", contains("Marker genes")));
    }
}
