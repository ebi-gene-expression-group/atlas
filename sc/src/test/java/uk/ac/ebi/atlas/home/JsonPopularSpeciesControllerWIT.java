package uk.ac.ebi.atlas.home;

import com.jayway.jsonpath.JsonPath;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JsonPopularSpeciesControllerWIT {
    @Inject
    private JdbcUtils jdbcTestUtils;
    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String ENDPOINT_URL = "/json/experiments/popular-species";

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void returnsAtLeastOnePopularSpecies() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[*].iconType").exists())
                .andExpect(jsonPath("$.[*].iconSrc").exists())
                .andExpect(jsonPath("$.[*].iconDescription").exists())
                .andExpect(jsonPath("$.[*].content").exists())
                .andExpect(jsonPath("$.[*].content.[*].text").exists())
                .andExpect(jsonPath("$.[*].content.[*].url").exists());
    }

    @Test
    void returnsSubsetOfPopularSpecies() throws Exception {
        List<String> species = jdbcTestUtils.fetchSpeciesForSingleCellExperiments();
        int numberOfPopularSpecies = ThreadLocalRandom.current().nextInt(1, species.size());

        mockMvc.perform(get(ENDPOINT_URL).param("limit", String.valueOf(numberOfPopularSpecies)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(numberOfPopularSpecies))));
    }

    @Test
    void returnsSpeciesInAExistingKingdom() throws Exception {
        Species randomSpecies = speciesFactory.create(jdbcTestUtils.fetchRandomSpeciesForSingleCellExperiments());

        MvcResult mvcResult = mockMvc
                .perform(get(ENDPOINT_URL)
                        .param("kingdom", randomSpecies.getKingdom()))
                .andReturn();

        List<String> returnedSpecies = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.[*].iconSrc");

        assertThat(returnedSpecies
                .stream()
                .map(s -> speciesFactory.create(s).getKingdom()))
                .allMatch(kingdom -> kingdom.equalsIgnoreCase(randomSpecies.getKingdom()));
    }

    @Test
    void returnsEmptyForUnknownKingdom() throws Exception {
        mockMvc.perform(get(ENDPOINT_URL).param("kingdom", "foo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
