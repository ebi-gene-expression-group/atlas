package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.solr.query.builders.FacetedPropertyValueQueryBuilder;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SolrQueryServiceTest {

    private static final String BIOENTITY_IDENTIFIER = "ENSG00000132604";
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    private static final String[] GENE_PAGE_PROPERTY_NAMES = new String[]{"synonym", "ortholog", "goterm", "interproterm", "ensfamily_description", "enstranscript", "mgi_description", "entrezgene", "uniprot", "mgi_id", "gene_biotype", "design_element"};

    private static final String[] TOOLTIP_PROPERTY_TYPES = new String[]{"synonym","goterm","interproterm"};

    private static final String SYMBOL = "symbol";

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
    public void initSubject() throws Exception {

        given(gxaSolrClientMock.query(solrQueryMock, false, PROPERTY_VALUE_FIELD)).willReturn(Sets.newHashSet("symbol"));
        given(facetedPropertyValueQueryBuilderMock.withPropertyNames(SYMBOL)).willReturn(facetedPropertyValueQueryBuilderMock);
        given(facetedPropertyValueQueryBuilderMock.withPropertyNames(GENE_PAGE_PROPERTY_NAMES)).willReturn(facetedPropertyValueQueryBuilderMock);
        given(facetedPropertyValueQueryBuilderMock.buildBioentityQuery(BIOENTITY_IDENTIFIER)).willReturn(solrQueryMock);
        given(solrQueryBuilderFactoryMock.createFacetedPropertyValueQueryBuilder()).willReturn(facetedPropertyValueQueryBuilderMock);

        subject = new BioEntityPropertyDao(solrQueryBuilderFactoryMock, gxaSolrClientMock, TOOLTIP_PROPERTY_TYPES);
    }

    @Test(expected = BioentityNotFoundException.class)
    public void shouldThrowException() throws Exception {
        SortedSetMultimap<String, String> emptyPropertyValues = TreeMultimap.create();
        given(gxaSolrClientMock.queryForProperties(solrQueryMock)).willReturn(emptyPropertyValues);

        subject.fetchGenePageProperties(BIOENTITY_IDENTIFIER, GENE_PAGE_PROPERTY_NAMES);
    }

    @Test
    public void shouldReturnSpecies() throws Exception {
        SortedSetMultimap<String, String> expectedPropertyValues = TreeMultimap.create();
        expectedPropertyValues.put("species", "cat");
        given(gxaSolrClientMock.queryForProperties(solrQueryMock)).willReturn(expectedPropertyValues);
        SortedSetMultimap<String, String> propertyValues = subject.fetchGenePageProperties(BIOENTITY_IDENTIFIER, GENE_PAGE_PROPERTY_NAMES);
        assertThat(propertyValues, is(expectedPropertyValues));
    }


    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.fetchPropertyValuesForGeneId(BIOENTITY_IDENTIFIER, SYMBOL), hasItem(SYMBOL));
    }

}
