package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentControllerWIT {
    private final String EXPERIMENT_ACCESSION = "E-MTAB-5061";
    private final String URL = "/experiments/{experimentAccession}";

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validExperimentAccession() throws Exception {
        this.mockMvc.perform(get(URL, EXPERIMENT_ACCESSION))
                .andExpect(status().isOk())
                .andExpect(view().name("experiment-page"))
                .andExpect(model().attribute("experimentAccession", EXPERIMENT_ACCESSION))
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
