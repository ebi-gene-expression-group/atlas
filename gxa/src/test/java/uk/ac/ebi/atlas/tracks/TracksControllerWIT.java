package uk.ac.ebi.atlas.tracks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class TracksControllerWIT {
    static final MessageFormat URL_TEMPLATE =
            new MessageFormat("/experiments-content/{0}/tracks/{0}.{1}.genes.expressions.bedGraph");

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getTrackFile() throws Exception {
        this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-MTAB-513", "g1"})))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getTrackOfUnknownExperiment() throws Exception {
        this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-FOO-BAR", "g1"})))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getUnknownTrackEndsUpIn404NotFound() throws Exception {
        MvcResult result = this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-MTAB-513", "gFooBar"})))
                .andExpect(status().isOk())
                .andReturn();

        this.mockMvc.perform(get(result.getResponse().getForwardedUrl()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getTrackPrivateExperimentWithoutAcessKey() throws Exception {
        this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-MTAB-3871", "g1"})))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getTrackPrivateExperimentWitIncorrectAcessKey() throws Exception {
        this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-MTAB-3871", "g1"}) + "?accessKey=foo-bar"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getTrackPrivateExperimentWithGoodAcessKey() throws Exception {
        this.mockMvc.perform(get(URL_TEMPLATE.format(new Object[]{"E-MTAB-3871", "g1"}) + "?accessKey=9fc53802-bc7f-4404-bce6-2eb7851d10bf"))
                .andExpect(status().isOk())
                .andReturn();
    }

}