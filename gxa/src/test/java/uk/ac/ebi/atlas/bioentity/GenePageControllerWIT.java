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
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.testutils.SolrUtils;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class GenePageControllerWIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Inject
    SolrUtils solrUtils;

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
    public void geneIdFieldIsPresent() throws Exception {
        String geneId = solrUtils.fetchRandomExpressionAtlasGene();

        this.mockMvc.perform(get("/genes/"+geneId))
                .andExpect(status().is(200))
                .andExpect(view().name("search-results"))
                .andExpect(model().attributeExists("gene_id"));
    }

}