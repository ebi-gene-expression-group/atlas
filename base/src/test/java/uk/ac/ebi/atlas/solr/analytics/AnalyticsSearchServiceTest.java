package uk.ac.ebi.atlas.solr.analytics;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;
import uk.ac.ebi.atlas.solr.analytics.MiscellaneousAnalyticsSearchDao;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsSearchServiceTest {

    static final SemanticQuery EMPTY_CONDITION_QUERY = SemanticQuery.create();

    static final SemanticQuery ZINC_FINGER_QUERY = SemanticQuery.create("zinc finger");
    static final String ZINC_FINGER_GENE_QUERY_SEARCH_RESPONSE = "{\n"+
            "  \"response\":{\"numFound\":78799,\"start\":0,\"docs\":[]\n"+
            "  },\n"+
            "  \"facet_counts\":{\n"+
            "    \"facet_queries\":{},\n"+
            "    \"facet_fields\":{\n"+
            "      \"species\":[\n"+
            "        \"homo sapiens\",59445,\n"+
            "        \"mus musculus\",9060,\n"+
            "        \"ovis aries\",5233,\n"+
            "        \"rattus norvegicus\",1118,\n"+
            "        \"caenorhabditis elegans\",802,\n"+
            "        \"papio anubis\",594,\n"+
            "        \"arabidopsis thaliana\",377,\n"+
            "        \"macaca mulatta\",372,\n"+
            "        \"monodelphis domestica\",324,\n"+
            "        \"xenopus tropicalis\",305,\n"+
            "        \"gallus gallus\",290,\n"+
            "        \"sorghum bicolor\",226,\n"+
            "        \"anolis carolinensis\",222,\n"+
            "        \"bos taurus\",202,\n"+
            "        \"triticum aestivum\",106,\n"+
            "        \"hordeum vulgare\",35,\n"+
            "        \"vitis vinifera\",34,\n"+
            "        \"oryza sativa\",22,\n"+
            "        \"zea mays\",15,\n"+
            "        \"glycine max\",12,\n"+
            "        \"danio rerio\",4,\n"+
            "        \"drosophila melanogaster\",1]},\n"+
            "    \"facet_dates\":{},\n"+
            "    \"facet_ranges\":{},\n"+
            "    \"facet_intervals\":{},\n"+
            "    \"facet_heatmaps\":{}}}";

    static final SemanticQuery FOOBAR_GENE_QUERY = SemanticQuery.create("foobar");
    static final String FOOBAR_GENE_QUERY_SEARCH_RESPONSE = "{\n" +
            "  \"response\":{\"numFound\":0,\"start\":0,\"docs\":[]\n" +
            "  },\n" +
            "  \"facet_counts\":{\n" +
            "    \"facet_queries\":{},\n" +
            "    \"facet_fields\":{\n" +
            "      \"species\":[]},\n" +
            "    \"facet_dates\":{},\n" +
            "    \"facet_ranges\":{},\n" +
            "    \"facet_intervals\":{},\n" +
            "    \"facet_heatmaps\":{}}}";

    @Mock
    MiscellaneousAnalyticsSearchDao miscellaneousAnalyticsSearchDaoMock;

    AnalyticsSearchService subject;

    @Before
    public void setUp() throws Exception {
        when(miscellaneousAnalyticsSearchDaoMock.getSpecies(ZINC_FINGER_QUERY, EMPTY_CONDITION_QUERY))
                .thenReturn(ZINC_FINGER_GENE_QUERY_SEARCH_RESPONSE);
        when(miscellaneousAnalyticsSearchDaoMock.getSpecies(FOOBAR_GENE_QUERY, EMPTY_CONDITION_QUERY))
                .thenReturn(FOOBAR_GENE_QUERY_SEARCH_RESPONSE);

        subject = new AnalyticsSearchService(miscellaneousAnalyticsSearchDaoMock);
    }

    @Test
    public void findSpecies() throws Exception {
        ImmutableList<String> speciesList = subject.findSpecies(ZINC_FINGER_QUERY, EMPTY_CONDITION_QUERY);

        assertThat(speciesList.get(0), is("homo sapiens"));
        assertThat(speciesList.get(speciesList.size() - 1), is("drosophila melanogaster"));
        assertThat(speciesList, hasSize(22));
    }

    @Test
    public void findNoSpecies() throws Exception {
        ImmutableList<String> speciesList = subject.findSpecies(FOOBAR_GENE_QUERY, EMPTY_CONDITION_QUERY);

        assertThat(speciesList, is(empty()));
    }
}