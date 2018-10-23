package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.testutils.SolrUtils;

import javax.inject.Inject;
import javax.sql.DataSource;

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
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonBioentityInformationControllerWIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private SolrUtils solrUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String urlTemplate = "/json/bioentity_information/{geneId}";

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void payloadIsValidJson() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

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
    public void geneIdsContainingDotIsNotTruncated() throws Exception {
        String geneId = solrUtils.fetchRandomGeneOfSpecies("Arabidopsis_lyrata"); // has gene IDs containing dots

        if(!geneId.isEmpty()) {
            this.mockMvc
                    .perform(get(urlTemplate, geneId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        }
    }

    @Test
    void geneNotFound() throws Exception {
        this.mockMvc
                .perform(get(urlTemplate, "unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void payloadContainsExpressionAtlasLink() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc
                .perform(get(urlTemplate, geneId))
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
