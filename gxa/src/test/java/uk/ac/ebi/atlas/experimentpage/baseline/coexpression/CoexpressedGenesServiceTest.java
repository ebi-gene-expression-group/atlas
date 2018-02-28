package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.bioentities.query.GeneQueryResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoexpressedGenesServiceTest {

    @Mock
    CoexpressedGenesDao coexpressedGenesDao;

    CoexpressedGenesService subject;

    static final String EX1_GENE_ID = "EX1_GENE_ID";
    static final String EX1_GENE_NAME = "example_one_gene";
    
    static final String EX2_GENE_ID = "EX2";

    static Map<Pair<String,String>, ImmutableList<String>> stateOfDatabase = new HashMap<>();
    static {
        stateOfDatabase.put(Pair.of(EX1_GENE_ID, "T0"), ImmutableList.of("C00", "C01", "C02"));
        stateOfDatabase.put(Pair.of(EX1_GENE_ID, "T1"), ImmutableList.of("C10", "C11"));
        stateOfDatabase.put(Pair.of(EX2_GENE_ID, "T1"), ImmutableList.of("C12"));
    }

    static Map<String, ImmutableList<String>> solrSearchResults = new HashMap<>();
    static {
        solrSearchResults.put(EX1_GENE_ID, ImmutableList.of(EX1_GENE_ID));
        solrSearchResults.put(EX1_GENE_NAME, ImmutableList.of(EX1_GENE_ID));
        solrSearchResults.put(EX2_GENE_ID,ImmutableList.of(EX2_GENE_ID));
    }

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        subject = new CoexpressedGenesService(coexpressedGenesDao);
        for(Map.Entry<Pair<String,String>, ImmutableList<String>> e: stateOfDatabase.entrySet()){
            when(coexpressedGenesDao.coexpressedGenesFor(e.getKey().getLeft(), e.getKey().getRight()))
                    .thenReturn(e.getValue());
        }
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms1(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0"), ImmutableMap.of());

        assertEquals(ImmutableSet.of("T0"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms2(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0", "T2"), ImmutableMap.of());

        assertEquals(ImmutableSet.of("T0","T2"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms3(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0"), ImmutableMap.of("T0", 3));

        assertEquals(ImmutableSet.of("T0","C00", "C01", "C02"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms4(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0", "T2"), ImmutableMap.of("T0", 3));

        assertEquals(ImmutableSet.of("T0","C00", "C01", "C02", "T2"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms5(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0", "T2"), ImmutableMap.of("T0", 1));

        assertEquals(ImmutableSet.of("T0","C00", "T2"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms6(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0","T1"), ImmutableMap.of("T0", 1));

        assertEquals(ImmutableSet.of("T0","C00", "T1"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms7(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0","T1"), ImmutableMap.of("T0", 1, "T1", 2));

        assertEquals(ImmutableSet.of("T0","C00", "T1", "C10", "C11"), r);
    }

    @Test
    public void testWithRetrievedIdsSameAsQueryTerms8(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0"), ImmutableMap.of("T0", 100000));

        assertEquals(ImmutableSet.of("T0","C00", "C01", "C02"), r);
    }
    @Test
    public void testWithRetrievedIdsSameAsQueryTerms9(){
        Collection<String> r =
                extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                        ImmutableList.of("T0"), ImmutableMap.of("T0", -1));

        assertEquals(ImmutableSet.of("T0"), r);
    }


    private ImmutableSet<String> extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds
            (Collection<String> geneQueryTerms, Map<String, Integer> requested) {

        GeneQueryResponse r = new GeneQueryResponse();
        for (String s : geneQueryTerms) {
            r.addGeneIds(s, ImmutableSet.of(s));
        }

        BaselineExperiment e = mock(BaselineExperiment.class);
        when(e.getAccession()).thenReturn(EX1_GENE_ID);

        Pair<GeneQueryResponse, List<String>> p =
                subject.extendGeneQueryResponseWithCoexpressions(r, e, requested, true);

        return ImmutableSet.<String>builder().addAll(p.getLeft().getAllGeneIds()).build();

    }

}