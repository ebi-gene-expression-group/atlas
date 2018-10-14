package uk.ac.ebi.atlas.experimentpage;

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

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonExperimentTSnePlotControllerWIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_tsne-fixture.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_tsne-delete.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void validJsonForExpressedGeneId() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        String geneId = jdbcTestUtils.fetchRandomGeneFromSingleCellExperiment(experimentAccession);
        // If our fixtures contained full experiments we could use any random perplexity with
        // fetchRandomPerplexityFromExperimentTSne(experimentAccession), but since we have a subset of all the rows, we
        // need to restrict this value to the perplexities actually available for the particular gene we choose.
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession, geneId);

        this.mockMvc
                .perform(get(
                        "/json/experiments/" + experimentAccession + "/tsneplot/" + perplexity +
                        "/expression/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.min", isA(Number.class)))
                .andExpect(jsonPath("$.min", is(greaterThan(0.0))))
                .andExpect(jsonPath("$.max", isA(Number.class)))
                .andExpect(jsonPath("$.max", is(greaterThan(0.0))))
                .andExpect(jsonPath("$.unit", is(oneOf("TPM"))))
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))));
    }

    @Test
    void validJsonForInvalidGeneId() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        this.mockMvc
                .perform(get(
                        "/json/experiments/" + experimentAccession + "/tsneplot/" + perplexity + "/expression/FOOBAR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.min").doesNotExist())
                .andExpect(jsonPath("$.max").doesNotExist())
                .andExpect(jsonPath("$.unit", is(oneOf("TPM"))))
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.series..expressionLevel", everyItem(is(0.0))));
    }

    @Test
    void noExpressionForEmptyGeneId() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        this.mockMvc
                .perform(get("/json/experiments/" + experimentAccession + "/tsneplot/" + perplexity + "/expression/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.min").doesNotExist())
                .andExpect(jsonPath("$.max").doesNotExist())
                .andExpect(jsonPath("$.unit", is(oneOf("TPM"))))
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.series..expressionLevel", everyItem(is(0.0))));
    }

    @Test
    void validJsonForValidK() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int k = jdbcTestUtils.fetchRandomKFromCellClusters(experimentAccession);
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        this.mockMvc
                .perform(get(
                        "/json/experiments/" + experimentAccession + "/tsneplot/" + perplexity +
                        "/clusters/k/" + k))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                // With full experiments this test could be even better:
                // .andExpect(jsonPath("$.series", hasSize(k)))
                // .andExpect(jsonPath("$.series[" + Integer.toString(RNG.nextInt(0, k)) + "].data").isNotEmpty());
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.series[0].data").isNotEmpty());
    }

    @Test
    void validJsonForInvalidK() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        this.mockMvc
                .perform(get(
                        "/json/experiments/" + experimentAccession + "/tsneplot/" + perplexity +
                        "/clusters/k/9000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.series", hasSize(1)));
    }
}
