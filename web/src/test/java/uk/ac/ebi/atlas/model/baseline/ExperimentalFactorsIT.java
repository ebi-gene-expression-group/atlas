
package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ExperimentalFactorsIT {

    @Inject
    private RnaSeqBaselineExperimentsCache experimentsCache;

    ExperimentalFactors subject;

    @Test
    public void testSomeExperiments() throws Exception {
        testFactorsFor("E-MTAB-513");
        testFactorsFor("E-MTAB-2706");

    }


    public void testFactorsFor(String accession) throws Exception {
        BaselineExperiment experiment = experimentsCache.getExperiment(accession);
        ExperimentalFactors subject = experiment.getExperimentalFactors();

        Set<Factor> allFactors  = subject.getAllFactors();
        assertThat(allFactors.size(), greaterThan(3));

        for(Factor f: allFactors) {

            assertTrue(subject.getFactors(f.getType()).contains(f));

            Set<Factor> complement = subject.getComplementFactors(Sets.newHashSet(f));
            Set<Factor> cooccurring = subject.getCoOccurringFactors(f);

            assertTrue(allFactors.containsAll(complement));
            assertTrue(allFactors.containsAll(cooccurring));
            assertFalse(complement.contains(f));
            assertFalse(cooccurring.contains(f));

            Set<Factor> intersection = new HashSet<>(complement);
            intersection.retainAll(cooccurring);
            assertTrue(intersection.isEmpty());

            List<AssayGroupFactor> complementAssays = subject.getComplementAssayGroupFactors(Sets.newHashSet(f));
            assertEquals(complement.size(), complementAssays.size());
            for(AssayGroupFactor agf : complementAssays){
                assertFalse(subject.getFactorGroup(agf.getAssayGroupId()).contains(f));
                assertTrue(complement.contains(agf.getFactor()));


                boolean isNonDefaultType = ! agf.getType().equals(subject.getDefaultQueryFactorType());
                boolean hasNonDefaultFactors = ! subject.getNonDefaultFactors(agf.getAssayGroupId()).isEmpty();
                assertEquals(isNonDefaultType, hasNonDefaultFactors);

            }

        }

        for(FactorGroup g: subject.getFactorGroupsInOrder()) {
            assertFalse(g.isEmpty());
            assertTrue(g.overlapsWith(allFactors));
        }
    }
}
