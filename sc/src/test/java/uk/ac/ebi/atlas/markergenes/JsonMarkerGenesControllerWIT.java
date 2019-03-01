package uk.ac.ebi.atlas.markergenes;

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

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JsonMarkerGenesControllerWIT {

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String urlTemplate = "/json/experiments/{experimentAccession}/marker-genes/{k}";

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-fixture.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-fixture.sql"),
                new ClassPathResource("fixtures/scxa_marker_gene_stats-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-delete.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-delete.sql"),
                new ClassPathResource("fixtures/scxa_marker_gene_stats-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void payloadIsValidJson() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccessionWithMarkerGenes();
        int k = jdbcTestUtils.fetchRandomKWithMarkerGene(experimentAccession);

        this.mockMvc
                .perform(get(urlTemplate, experimentAccession, k))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].clusterIdWhereMarker", isA(Number.class)))
                .andExpect(jsonPath("$[0].x", isA(Number.class)))
                .andExpect(jsonPath("$[0].y", isA(Number.class)))
                .andExpect(jsonPath("$[0].name", isA(String.class)))
                .andExpect(jsonPath("$[0].value", isA(Number.class)))
                .andExpect(jsonPath("$[0].pValue", isA(Number.class)));
    }

    @Test
    void experimentWithoutMarkerGenesReturnsEmptyJson() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccessionWithoutMarkerGenes();
        int k = jdbcTestUtils.fetchRandomKFromCellClusters(experimentAccession);

        this.mockMvc
                .perform(get(urlTemplate, experimentAccession, k))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isEmpty());
    }
}
