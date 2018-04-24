package uk.ac.ebi.atlas.search;

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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class JsonBioentityInformationControllerWIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void payloadIsValidJson() throws Exception {
        String geneId = "ENSMUSG00000021789";

        this.mockMvc
                .perform(get(
                        "/json/bioentity_information/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].type", isA(String.class)))
                .andExpect(jsonPath("$[0].name", isA(String.class)))
                .andExpect(jsonPath("$[0].values", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].values[0].text", isA(String.class)))
                .andExpect(jsonPath("$[0].values[0].url", isA(String.class)))
                .andExpect(jsonPath("$[0].values[0].relevance", isA(Number.class)));
    }

    @Test
    public void geneNotFound() throws Exception {
        this.mockMvc
                .perform(get("/json/bioentity_information/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void payloadContainsExpressionAtlasLink() throws Exception{
        String geneId = "ENSMUSG00000021789";
        this.mockMvc
                .perform(get("/json/bioentity_information/" + geneId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[-1:].type",contains("expression_atlas")))
                .andExpect(jsonPath("$[-1:].name", contains("ExpressionAtlas")))
                .andExpect(jsonPath("$[-1:].values", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[-1:].values[0].text", contains(geneId)))
                .andExpect(jsonPath("$[-1:].values[0].url", contains("https://www.ebi.ac.uk/gxa/genes/"+geneId)))
                .andExpect(jsonPath("$[-1:].values[0].relevance", contains("0")));
    }
}