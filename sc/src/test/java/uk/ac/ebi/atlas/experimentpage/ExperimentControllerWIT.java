package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(Parameterized.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentControllerWIT {
    private final String URL = "/experiments/{experimentAccession}";

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;
    private TestContextManager testContextManager;

    @Parameterized.Parameter
    public String experimentAccession;

    @Parameterized.Parameters
    public static Iterable<?> data() {
        // I haven’t been able to think of a better way to get all public experiments from the data source configured
        // applicationContext.xml. Since the application context isn’t loaded until @Before, we need to explicitly
        // build it first. Interface ApplicationContextAware (as suggested, for example, in
        // http://sujitpal.blogspot.co.uk/2007/03/accessing-spring-beans-from-legacy-code.html) doesn’t work because of
        // the very same reason: the context, and the bean that can supply a static reference to the context, won’t be
        // ready at this point. Fortunately Hikari doesn’t complain about opening two connection pools.
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // I guess we could also retrive scxaEperimentTrader
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        return jdbcTemplate.queryForList("SELECT accession FROM scxa_public_experiment", String.class);
//        return Arrays.asList("E-GEOD-106540", "E-MTAB-5061");
    }

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validExperimentAccession() throws Exception {
            this.mockMvc.perform(get(URL, experimentAccession))
                    .andExpect(status().isOk())
                    .andExpect(view().name("experiment-page"))
                    .andExpect(model().attribute("experimentAccession", experimentAccession))
                    .andExpect(model().attribute("type", ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE))
                    .andExpect(model().attributeExists("content"));
    }

    @Test
    public void invalidExperimentAccession() throws Exception {
        this.mockMvc.perform(get(URL, "FOO"))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }
}
