package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
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

import java.util.List;
import java.util.Map;

import static java.util.Collections.shuffle;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonGeneSearchControllerWIT {
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
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void unknownGene() throws Exception {
        this.mockMvc.perform(get("/json/search").param("q", "FOO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results").isEmpty())
                .andExpect(jsonPath("$.reason").value(startsWith("Gene unknown")));
    }

    @Test
    void unexpressedGene() throws Exception {
        this.mockMvc.perform(get("/json/search").param("symbol", "foxo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results").isEmpty())
                .andExpect(jsonPath("$.reason").value(startsWith("No expression found")));
    }

    @Test
    void validJsonForValidGeneId() throws Exception {
        String geneId = jdbcTestUtils.fetchRandomGene();

        this.mockMvc.perform(get("/json/search").param("ensgene", geneId))
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

        this.mockMvc.perform(get("/json/search").param("ensgene", geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].element.experimentAccession", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.results[0].facets[0].group", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].value", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].label", isA(String.class)))
                .andExpect(jsonPath("$.results[0].facets[0].description", isA(String.class)))
                .andExpect(jsonPath("$.checkboxFacetGroups", contains("Marker genes", "Species")));
    }
      
    @Test
    void speciesParamCanAppearBeforeGeneQuery() throws Exception {
        this.mockMvc.perform(get("/json/search").param("species", "homo sapiens").param("symbol", "aspm"))
                .andExpect(status().isOk());
    }
}
