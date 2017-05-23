package uk.ac.ebi.atlas.widget;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class JsonBaselineExperimentsControllerWIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void baselineExperiments() throws Exception {
        String geneQuery = SemanticQuery.create(SemanticQueryTerm.create("zinc finger")).toUrlEncodedJson();
        this.mockMvc.perform(
                get(JsonBaselineExperimentsController.url)
                        .param("geneQuery", geneQuery)
                        .param("species", "caenorhabditis elegans"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(geneQuery)))
                .andExpect(content().string(Matchers.containsString("expressions")));

    }

    @Test
    public void baselineExperimentsFailsWithoutQuery() throws Exception {
        this.mockMvc.perform(
                get(JsonBaselineExperimentsController.url))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("error")));

    }
}