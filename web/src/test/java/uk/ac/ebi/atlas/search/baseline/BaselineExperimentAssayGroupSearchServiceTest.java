package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import org.junit.Test;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class BaselineExperimentAssayGroupSearchServiceTest {


    @Test
    public void createSetOfIndexedAssayGroups() {

        SetMultimap<String, String> multiMap = HashMultimap.create();

        multiMap.put("EXP1", "G1");
        multiMap.put("EXP1", "G2");
        multiMap.put("EXP2", "G3");

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = BaselineExperimentAssayGroupSearchService.createSetOfIndexedAssayGroups(multiMap);

        assertThat(indexedAssayGroups, containsInAnyOrder(new IndexedAssayGroup("EXP1", "G1"), new IndexedAssayGroup("EXP1", "G2"), new IndexedAssayGroup("EXP2", "G3")));
    }

}
