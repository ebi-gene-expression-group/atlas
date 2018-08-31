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
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
class JsonBioentityInformationControllerWIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void payloadIsValidJson() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc
                .perform(get("/json/bioentity_information/" + geneId))
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
    void geneNotFound() throws Exception {
        this.mockMvc
                .perform(get("/json/bioentity_information/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void payloadContainsExpressionAtlasLink() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc
                .perform(get("/json/bioentity_information/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[-1:].type", contains("expression_atlas")))
                .andExpect(jsonPath("$[-1:].name", contains("Expression Atlas")))
                .andExpect(jsonPath("$[-1:].values", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[-1:].values[0].text", contains(geneId)))
                .andExpect(jsonPath("$[-1:].values[0].url", contains("https://www.ebi.ac.uk/gxa/genes/" + geneId)))
                .andExpect(jsonPath("$[-1:].values[0].relevance", contains(0)));
    }
}
