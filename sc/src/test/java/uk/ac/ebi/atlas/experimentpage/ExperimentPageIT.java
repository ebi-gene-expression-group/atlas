package uk.ac.ebi.atlas.experimentpage;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ExperimentPageIT {

    @Inject
    ApplicationProperties applicationProperties;

    @Test
    public void pubMedIds(){
        assertThat(applicationProperties.getPubMedURL("25867922"), Matchers.notNullValue());
    }
}
