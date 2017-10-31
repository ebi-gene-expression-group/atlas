package uk.ac.ebi.atlas.widget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class JsonBaselineRefExperimentControllerWIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void jsonBaselineRefExperiment() throws Exception {
        this.mockMvc.perform(
                get("/json/baseline_refexperiment")
                        .param("geneQuery", "zinc finger")
                        .param("species", "caenorhabditis elegans"))
                .andExpect(status().isOk());
    }

    @Test
    public void jsonBaselineRefExperimentWithUnknownSpecies() throws Exception {
        this.mockMvc.perform(get("/json/baseline_refexperiment")
                    .param("geneQuery", "zinc finger")
                    .param("species", "foobar"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
                // Unfortunately, Spring 3.2.2 requires json-path 0.8.1 which is super ancient and breaks all sorts of
                // things... :(
                //.andExpect(jsonPath("$.error").value(is("blah")));
    }

}