package uk.ac.ebi.atlas.utils;

import org.hamcrest.Matchers;
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
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 21/07/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ExperimentSorterIT {
    
    @Inject
    ExperimentSorter subject;

    @Test
    public void testReverseSortExperimentsPerSize() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        Iterator<String> iterator = experimentAccessionsPerSizeDescending.iterator();

        String firstExperiment = iterator.next();


        String lastExperiment = firstExperiment;
        while (iterator.hasNext()) {
            lastExperiment = iterator.next();
        }

        Long firstSize = -1L;
        Long lastSize = -1L;

        for(Map.Entry<Long, Collection<String>> e: subject.reverseSortAllExperimentsPerSize().asMap().entrySet()){
            if(e.getValue().contains(lastExperiment)){
                lastSize = e.getKey();
            }
            if(e.getValue().contains(firstExperiment)){
                firstSize = e.getKey();
            }
        }
        assertTrue(firstSize > 0 && lastSize > 0 && firstSize >= lastSize);
    }

    // TODO https://www.pivotaltracker.com/story/show/101118548
    @Ignore
    public void reverseSortExperimentsPerSizeContainsAllExperiments() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        assertThat(experimentAccessionsPerSizeDescending.size(), Matchers.greaterThan(50));
    }
}