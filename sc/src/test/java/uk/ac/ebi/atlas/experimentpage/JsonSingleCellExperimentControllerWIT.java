package uk.ac.ebi.atlas.experimentpage;

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

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.isOneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class JsonSingleCellExperimentControllerWIT {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validJsonForExpressedGeneId() throws Exception {
        int[] perplexities = {1, 5, 10, 15, 20};
        int k = ThreadLocalRandom.current().nextInt(2, 11);
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(perplexities.length)];

        this.mockMvc
                .perform(get(
                        "/json/experiments/E-MTAB-5061/tsneplot/" + perplexity +
                        "/clusters/" + k + "/expression/ENSG00000000003"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.min", isA(Number.class)))
                .andExpect(jsonPath("$.min", is(greaterThan(0.0))))
                .andExpect(jsonPath("$.max", isA(Number.class)))
                .andExpect(jsonPath("$.max", is(greaterThan(0.0))))
                .andExpect(jsonPath("$.unit", isOneOf("TPM")))
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))));
    }

    @Test
    public void validJsonForInvalidGeneId() throws Exception {
        int[] perplexities = {1, 5, 10, 15, 20};
        int k = ThreadLocalRandom.current().nextInt(2, 11);
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(perplexities.length)];

        this.mockMvc
                .perform(get(
                        "/json/experiments/E-MTAB-5061/tsneplot/" + perplexity +
                                "/clusters/" + k + "/expression/FOOBAR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.min").doesNotExist())
                .andExpect(jsonPath("$.max").doesNotExist())
                .andExpect(jsonPath("$.unit", isOneOf("TPM")))
                .andExpect(jsonPath("$.series", hasSize(greaterThan(0))));
    }
}