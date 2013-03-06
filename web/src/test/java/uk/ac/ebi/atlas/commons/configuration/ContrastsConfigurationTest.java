package uk.ac.ebi.atlas.commons.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ContrastsConfigurationTest {


    @Inject
    private ConfigurationTrader configurationTrader;

    private ContrastsConfiguration subject;

    @Before
    public void setUp() throws Exception {
        subject = configurationTrader.getContrastsConfiguration("E-GEOD-22351");
    }

    @Test
    public void testGetAssayAccessions() throws Exception {
        Set<String> assayAccession = subject.getAssayAccessions("g1");
        assertThat(assayAccession, containsInAnyOrder("","",""));
    }

    @Test
    public void testGetContrasts() throws Exception {

    }
}
