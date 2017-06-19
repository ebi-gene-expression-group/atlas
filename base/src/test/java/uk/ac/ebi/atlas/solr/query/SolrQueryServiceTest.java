package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.HashMap;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SolrQueryServiceTest {

    private static final String BIOENTITY_IDENTIFIER = "ENSG00000132604";
    private static final String GENE_SYMBOL = "TERF2";

    private BioEntityPropertyDao subject;

    @Mock
    private BioentitiesSolrClient gxaSolrClientMock;

    @Before
    public void setUp() throws Exception {
        HashMap<BioentityPropertyName, Set<String>> propertiesMap = Maps.newHashMap();
        propertiesMap.put(BioentityPropertyName.SYMBOL, Sets.newHashSet(GENE_SYMBOL));
        given(gxaSolrClientMock.getMap(BIOENTITY_IDENTIFIER, ImmutableList.of(BioentityPropertyName.SYMBOL))).willReturn(propertiesMap);

        subject = new BioEntityPropertyDao(gxaSolrClientMock);
    }

    @Test(expected = BioentityNotFoundException.class)
    public void shouldThrowException() throws Exception {
        subject.fetchGenePageProperties(BIOENTITY_IDENTIFIER);
    }

    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.fetchPropertyValuesForGeneId(BIOENTITY_IDENTIFIER, BioentityPropertyName.SYMBOL), hasItem(GENE_SYMBOL));
    }

}
