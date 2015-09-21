package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;
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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class BaselineExperimentAssayGroupsDaoIT {

    private static final String E_MTAB_599 = "E-MTAB-599";

    @Inject
    private BaselineExperimentAssayGroupsDao subject;

    @Test
    public void findsExpressionByExperimentAssayGroup() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "g6");
        SetMultimap<String,String> multimap = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>absent());
        assertThat(multimap.entries(), hasSize(1));
        assertThat(multimap.asMap(), hasEntry(equalTo(E_MTAB_599), contains("g6")));
    }

    @Test
    public void doesNotFindNonExistentExperimentAssayGroup() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "DOES_NOT_EXIST");
        SetMultimap<String, String> multimap = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>absent());
        assertThat(multimap.entries(), is(empty()));
    }

    @Test
    public void findsExpressionByGeneId() throws Exception {
        SetMultimap<String, String> results = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.<Collection<String>>of(ImmutableList.of("ENSMUSG00000093014")));
        assertThat(results.entries(), hasSize(1));
        assertThat(results.asMap(), hasEntry(equalTo(E_MTAB_599), contains("g6")));
        //assertThat(results.asMap(), containsInAnyOrder(E_MTAB_599_g5, E_MTAB_599_g3, E_MTAB_599_g6));
    }

    @Test
    public void doesNotFindNonExistantGenes() throws Exception {
        SetMultimap<String, String> results = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.<Collection<String>>of(ImmutableList.of("DOES_NOT_EXIST")));
        assertThat(results.entries(), is(empty()));
    }

    @Test
    public void findsExpressionByExperimentAssayGroupAndGeneId() throws Exception {
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup(E_MTAB_599, "g6");
        SetMultimap<String, String> results = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(Optional.of(ImmutableList.of(E_MTAB_599_g6)), Optional.<Collection<String>>of(ImmutableList.of("ENSMUSG00000093014")));
        assertThat(results.entries(), hasSize(1));
        assertThat(results.asMap(), hasEntry(equalTo(E_MTAB_599), contains("g6")));
    }

}
