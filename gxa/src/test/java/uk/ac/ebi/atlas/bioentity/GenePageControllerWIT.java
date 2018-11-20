package uk.ac.ebi.atlas.bioentity;

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
import uk.ac.ebi.atlas.configuration.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class GenePageControllerWIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void geneNotFound() throws Exception {
        this.mockMvc.perform(get("/genes/foobar"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-page")); // Can be a view name
    }

    @Test
    public void genePresentButWithExpressionBelowThreshold() throws Exception {
        this.mockMvc.perform(get("/genes/ENSMUSG00000006386"))
                .andExpect(status().isOk())
                .andExpect(view().name("no-results"));
    }
}
