package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class RnaSeqBslnExpressionsDaoIT {

    private static final String E_MTAB_599 = "E-MTAB-599";

    @Inject
    private RnaSeqBslnExpressionsDao subject;

    @Test
    public void findsExpressionByExperimentAssayGroup() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "g6");
        assertThat(subject.fetchExperimentAssayGroups(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>absent()), contains(E_MTAB_599_g6));
    }

    @Test
    public void doesNotFindNonExistantExperimentAssayGroup() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "DOES_NOT_EXIST");
        assertThat(subject.fetchExperimentAssayGroups(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>absent()), is(empty()));
    }

    @Test
    public void findsExpressionByGeneId() throws Exception {
        IndexedAssayGroup E_MTAB_599_g5 = new IndexedAssayGroup(E_MTAB_599, "g5");
        IndexedAssayGroup E_MTAB_599_g3 = new IndexedAssayGroup(E_MTAB_599, "g3");
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "g6");
        ImmutableSet<IndexedAssayGroup> results = subject.fetchExperimentAssayGroups(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.<Collection<String>>of(ImmutableList.of("ENSMUSG00000093014")));
        assertThat(results, containsInAnyOrder(E_MTAB_599_g5, E_MTAB_599_g3, E_MTAB_599_g6));
    }

    @Test
    public void doesNotFindNonExistantGenes() throws Exception {
        ImmutableSet<IndexedAssayGroup> results = subject.fetchExperimentAssayGroups(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.<Collection<String>>of(ImmutableList.of("DOES_NOT_EXIST")));
        assertThat(results, is(empty()));
    }

    @Test
    public void findsExpressionByExperimentAssayGroupAndGeneId() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "g6");
        ImmutableSet<IndexedAssayGroup> results = subject.fetchExperimentAssayGroups(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>of(ImmutableList.of("ENSMUSG00000093014")));
        assertThat(results, contains(E_MTAB_599_g6));
    }

}
