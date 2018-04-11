package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(Parameterized.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentControllerWIT {
    private final String URL = "/experiments/{experimentAccession}";

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;
    private TestContextManager testContextManager;

    @Parameterized.Parameter
    public String experimentAccession;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        // TODO Make static utility class using JdbcTestUtils/ScriptUtils that retrieves a list of all available experiment accessions
        return Arrays.asList("E-GEOD-106540", "E-MTAB-5061");
    }

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validExperimentAccession() throws Exception {
            this.mockMvc.perform(get(URL, experimentAccession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("experiment-page"))
                    .andExpect(model().attribute("experimentAccession", experimentAccession))
                    .andExpect(model().attribute("type", ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE))
                    .andExpect(model().attributeExists("content"));
    }

    @Test
    public void invalidExperimentAccession() throws Exception {
        this.mockMvc.perform(get(URL, "FOO"))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }
}
