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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class DASFeaturesControllerWIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getDifferentialJsonResults() throws Exception {

        String geneId = "ENSG00000000457";
        String conditionQuery = "";

        this.mockMvc.perform(
                get("/das/s4/features")
                        .param("geneId", geneId)
                        .param("conditionQuery", conditionQuery))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("das-features"));
    }

    @Test
    public void differentialJsonResultsFailWithoutQuery() throws Exception
    {
        this.mockMvc.perform(
                get("/das/s4/features"))
                .andExpect(status().isOk());
    }

}