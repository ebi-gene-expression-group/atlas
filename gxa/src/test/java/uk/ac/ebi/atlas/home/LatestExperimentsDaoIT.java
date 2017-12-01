package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class LatestExperimentsDaoIT {

    @Inject
    LatestExperimentsDao subject;

    @Test
    public void experimentsCount() throws Exception {
        assertThat(
                subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values())),
                is(greaterThan(50L)));

        assertThat(
                subject.fetchPublicExperimentsCount(ImmutableSet.<ExperimentType>of()),
                is(subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values()))));

        assertThat(
                subject.fetchPublicExperimentsCount(
                        ImmutableSet.of(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)),
                is(lessThan(subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values())))));

    }

    @Test
    public void experimentAccessions() throws Exception {
        assertThat(
                subject.fetchLatestExperimentAccessions(ImmutableSet.copyOf(ExperimentType.values())),
                hasSize(5));
    }

}