package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class ExperimentalFactorsIT {

    @Inject
    private RnaSeqBaselineExperimentsCache experimentsCache;

    private ExperimentalFactors subject;

    @Test
    public void testSomeExperiments() throws Exception {
        testFactorsFor("E-MTAB-513");
        testFactorsFor("E-MTAB-2706");

    }

    public void testFactorsFor(String accession) throws Exception {
        BaselineExperiment experiment = experimentsCache.getExperiment(accession);
        subject = experiment.getExperimentalFactors();

        Set<Factor> allFactors  = subject.getAllFactors();
        assertThat(allFactors.size(), greaterThan(3));

        for(Factor f: allFactors) {
            assertThat(subject.getFactors(f.getType()), hasItem(f));

            Set<Factor> complement = subject.getComplementFactors(Sets.newHashSet(f));
            Set<Factor> cooccurring = subject.getCoOccurringFactors(f);

            assertThat(allFactors, hasItems(complement.toArray(new Factor[complement.size()])));
            assertThat(allFactors, hasItems(cooccurring.toArray(new Factor[cooccurring.size()])));
            assertThat(complement, not(hasItem(f)));
            assertThat(cooccurring, not(hasItem(f)));

            Set<Factor> intersection = new HashSet<>(complement);
            intersection.retainAll(cooccurring);
            assertThat(intersection, hasSize(0));

            List<AssayGroupFactor> complementAssays = subject.getComplementAssayGroupFactors(Sets.newHashSet(f));
            assertThat(complement.size(), is(complementAssays.size()));
            for(AssayGroupFactor agf : complementAssays) {
                assertThat(subject.getFactorGroup(agf.getAssayGroupId()), not(hasItem(f)));
                assertThat(complement, hasItem(agf.getFactor()));

                boolean isNonDefaultType = ! agf.getType().equals(subject.getDefaultQueryFactorType());
                boolean hasNonDefaultFactors = ! subject.getNonDefaultFactors(agf.getAssayGroupId()).isEmpty();
                assertThat(isNonDefaultType, is(hasNonDefaultFactors));
            }
        }

        for(FactorGroup g: subject.getFactorGroupsInOrder()) {
            assertThat(g.size(), is(greaterThan(0)));
            assertThat(g.overlapsWith(allFactors), is(true));
        }
    }

}
