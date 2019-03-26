package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonSpeciesSummaryControllerWIT {
    @Inject
    private SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String ENDPOINT_URL = "/json/species-summary";

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void responseIsWellFormed() throws Exception {
        ImmutableSet<String> kingdoms = speciesPropertiesTrader.getAll().stream()
                .map(SpeciesProperties::kingdom)
                .collect(toImmutableSet());

        String json = mockMvc.perform(get(ENDPOINT_URL)).andReturn().getResponse().getContentAsString();
        ReadContext jsonCtx = JsonPath.parse(json);

        List<String> jsonKingdoms = jsonCtx.read("$.speciesSummary[*].kingdom");
        assertThat(jsonKingdoms).containsAnyElementsOf(kingdoms);

        List<String> jsonCardTypes = jsonCtx.read("$.speciesSummary[*].cards[*].iconType");
        assertThat(jsonCardTypes).containsAnyOf("species");

        List<String> jsonCardSources = jsonCtx.read("$.speciesSummary[*].cards[*].iconSrc");
        assertThat(jsonCardSources)
                .allMatch(iconSrc -> speciesPropertiesTrader.get(iconSrc) != SpeciesProperties.UNKNOWN);
    }
}
