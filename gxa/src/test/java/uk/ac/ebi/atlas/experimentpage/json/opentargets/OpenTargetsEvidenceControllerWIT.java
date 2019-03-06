package uk.ac.ebi.atlas.experimentpage.json.opentargets;

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

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_ANY;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenTargetsEvidenceControllerWIT {
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
    void setContentTypeAndEncodeInUtf8() throws Exception {
        String accession = "E-GEOD-40611";
        this.mockMvc.perform(
                get("/json/experiments/" + accession + "/evidence")
                        .param("type", MICROARRAY_ANY.toString())
                        .param("logFoldChangeCutoff", "1.0")
                        .param("pValueCutoff", "0.05")
                        .param("maxGenesPerContrast", "10"))
                .andExpect(header().string("content-type", "application/json-seq; charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"));
    }
}