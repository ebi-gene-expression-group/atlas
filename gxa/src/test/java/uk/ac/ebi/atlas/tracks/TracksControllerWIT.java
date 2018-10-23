package uk.ac.ebi.atlas.tracks;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TracksControllerWIT {
    private static final MessageFormat BASELINE_URL_TEMPLATE =
            new MessageFormat("/experiments-content/{0}/tracks/{0}.{1}.genes.expressions.bedGraph");
    private static final MessageFormat DIFFERENTIAL_URL_TEMPLATE =
            new MessageFormat("/experiments-content/{0}/tracks/{0}.{1}.genes.log2foldchange.bedGraph");

    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

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
    void getTrackFile() throws Exception {
        this.mockMvc.perform(get(BASELINE_URL_TEMPLATE.format(new Object[] {"E-MTAB-5423", "g391"})))
                .andExpect(status().isOk());
    }

    @Test
    void getTrackOfUnknownExperiment() throws Exception {
        this.mockMvc.perform(get(BASELINE_URL_TEMPLATE.format(new Object[] {"E-FOO-BAR", "g1"})))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnknownTrackEndsUpIn404NotFound() throws Exception {
        MvcResult result =
                this.mockMvc.perform(get(BASELINE_URL_TEMPLATE.format(new Object[] {"E-MTAB-5423", "gFooBar"})))
                        .andExpect(status().isOk())
                        .andReturn();

        this.mockMvc.perform(get(result.getResponse().getForwardedUrl()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTrackPrivateExperimentWithoutAcessKey() throws Exception {
        this.mockMvc.perform(get(DIFFERENTIAL_URL_TEMPLATE.format(new Object[] {"E-MTAB-4106", "g5_g3"})))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTrackPrivateExperimentWitIncorrectAcessKey() throws Exception {
        this.mockMvc.perform(
                get(DIFFERENTIAL_URL_TEMPLATE.format(new Object[] {"E-MTAB-4106", "g5_g3"}) + "?accessKey=foo-bar"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTrackPrivateExperimentWithGoodAcessKey() throws Exception {
        this.mockMvc.perform(
                get(DIFFERENTIAL_URL_TEMPLATE.format(
                        new Object[] {"E-MTAB-4106", "g5_g3"}) + "?accessKey=1483a23f-4853-4583-b10c-5ab333bf522f"))
                .andExpect(status().isOk());
    }
}
