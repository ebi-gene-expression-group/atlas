package uk.ac.ebi.atlas.experimentpage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// @SpringJUnitJupiterWebConfig(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
// The meta annotation above includes @ExtendWith(SpringExtension.class), @WebAppConfiguration and
// @ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"}) but it
// confuses IDEA and fails to detect the component scan beans (although the tests run fine). We can wait until we move
// to Spring 5.
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)  // @BeforeAll and @MethodSource need not be static :)
class ExperimentControllerWIT {
    private static final String URL = "/experiments/{experimentAccession}";

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    private MockMvc mockMvc;

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

    @BeforeAll
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @ParameterizedTest
    @MethodSource("publicExperimentsProvider")
    void validExperimentAccession(String experimentAccession) throws Exception {
        mockMvc.perform(get(URL, experimentAccession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("experiment-page"))
                    .andExpect(model().attribute("experimentAccession", experimentAccession))
                    .andExpect(model().attribute(
                            "type", ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE.getHumanDescription()))
                    .andExpect(model().attributeExists("content"));
    }

    @Test
    void invalidExperimentAccession() throws Exception {
        mockMvc.perform(get(URL, "FOO"))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }

    private Iterable<String> publicExperimentsProvider() {
        return jdbcTestUtils.fetchPublicSingleCellExperimentAccessions();
    }
}
