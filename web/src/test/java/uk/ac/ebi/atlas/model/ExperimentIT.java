package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExperimentIT {

    @Inject
    private ExperimentsCache experimentsCache;

    private Experiment subject;

    @Before
    public void initSubject(){

        subject = experimentsCache.getExperiment("E-GEOD-26284");

    }

    @Test
    public void t(){
        //assertThat(subject.getFactors(""));
    }

}
