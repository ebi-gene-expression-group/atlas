package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.builders.FacetedPropertyValueQueryBuilder;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SolrQueryServiceTest {

    private static final String BIOENTITY_IDENTIFIER = "ENSG00000132604";
    private static final String GENE_SYMBOL = "TERF2";
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    private BioEntityPropertyDao subject;

    @Mock
    private SolrQueryBuilderFactory solrQueryBuilderFactoryMock;

    @Mock
    private FacetedPropertyValueQueryBuilder facetedPropertyValueQueryBuilderMock;

    @Mock
    private GxaSolrClient gxaSolrClientMock;

    @Mock
    private SolrQuery solrQueryMock;

    @Mock
    private QueryResponse queryResponseMock;

    @Before
    public void setUp() throws Exception {
        given(gxaSolrClientMock.query(solrQueryMock, false, PROPERTY_VALUE_FIELD)).willReturn(Sets.newHashSet("symbol"));

        HashMap<BioentityPropertyName, Set<String>> propertiesMap = Maps.newHashMap();
        propertiesMap.put(BioentityPropertyName.SYMBOL, Sets.newHashSet(GENE_SYMBOL));
        given(gxaSolrClientMock.getMap(BIOENTITY_IDENTIFIER, ImmutableList.of(BioentityPropertyName.SYMBOL))).willReturn(propertiesMap);

        given(facetedPropertyValueQueryBuilderMock.withPropertyNames(BioentityPropertyName.SYMBOL)).willReturn(facetedPropertyValueQueryBuilderMock);
        given(facetedPropertyValueQueryBuilderMock.withPropertyNames(BioentityPropertyName.values())).willReturn(facetedPropertyValueQueryBuilderMock);
        given(facetedPropertyValueQueryBuilderMock.buildBioentityQuery(BIOENTITY_IDENTIFIER)).willReturn(solrQueryMock);
        given(solrQueryBuilderFactoryMock.createFacetedPropertyValueQueryBuilder()).willReturn(facetedPropertyValueQueryBuilderMock);

        subject = new BioEntityPropertyDao(gxaSolrClientMock);
    }

    @Test(expected = BioentityNotFoundException.class)
    public void shouldThrowException() throws Exception {
        Map<BioentityPropertyName, Set<String>> emptyPropertyValues = new HashMap<>();
        given(gxaSolrClientMock.getMap(BIOENTITY_IDENTIFIER, ExperimentDataPoint.bioentityPropertyNames)).willReturn(emptyPropertyValues);

        subject.fetchGenePageProperties(BIOENTITY_IDENTIFIER);
    }

    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.fetchPropertyValuesForGeneId(BIOENTITY_IDENTIFIER, BioentityPropertyName.SYMBOL), hasItem(GENE_SYMBOL));
    }

}
