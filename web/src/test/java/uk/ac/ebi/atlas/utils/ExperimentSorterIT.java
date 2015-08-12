package uk.ac.ebi.atlas.utils;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experiments.NumberOfExperiments;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 21/07/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentSorterIT {

    private static final String BIGGEST_TEST_EXPERIMENT_ACCESSION_E_GEOD_12108 = "E-GEOD-12108";
    private static final String SMALLEST_TEST_EXPERIMENT_ACCESSION_E_MTAB_2039 = "E-MTAB-2039";

    @Inject
    ExperimentSorter subject;

    @Test
    public void testReverseSortExperimentsPerSize() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        Iterator<String> iterator = experimentAccessionsPerSizeDescending.iterator();

        String firstExperiment = iterator.next();
        assertThat(firstExperiment, is(BIGGEST_TEST_EXPERIMENT_ACCESSION_E_GEOD_12108));

        String lastExperiment = firstExperiment;
        while (iterator.hasNext()) {
            lastExperiment = iterator.next();
        }

        assertThat(lastExperiment, is(SMALLEST_TEST_EXPERIMENT_ACCESSION_E_MTAB_2039));
    }

    // TODO https://www.pivotaltracker.com/story/show/101118548
    @Ignore
    public void reverseSortExperimentsPerSizeContainsAllExperiments() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        assertThat(experimentAccessionsPerSizeDescending.size(), is(NumberOfExperiments.ALL));
    }
}