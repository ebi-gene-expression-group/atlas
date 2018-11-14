package uk.ac.ebi.atlas.acceptance.rest.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class StaticPageControllerWIT {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void about() throws Exception {
        this.mockMvc.perform(get("/about.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("static"));
    }

    @Test
    public void noPage() throws Exception {
        this.mockMvc.perform(get("/nopage.html"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error-page"));
    }

    @Test
    public void help() throws Exception {
        this.mockMvc.perform(get("/help/index.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("static"));
    }

}
