package uk.ac.ebi.atlas.controllers.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class DASFeaturesControllerWIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getResultsWithSegment() throws Exception {
        String geneId = "ENSG00000000457";

        this.mockMvc.perform(
                get("/das/s4/features")
                        .param("segment", geneId)
                        .param("conditionQuery", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("das-features"));
    }

    @Test
    public void getResultsWithCondition() throws Exception {
        this.mockMvc.perform(
                get("/das/s4/features")
                        .param("segment", "")
                        .param("conditionQuery", "cancer"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("das-features"));
    }


    @Test
    public void failWithoutQuery() throws Exception {
        this.mockMvc.perform(
                get("/das/s4/features"))
                .andExpect(status().isBadRequest());
    }
}
