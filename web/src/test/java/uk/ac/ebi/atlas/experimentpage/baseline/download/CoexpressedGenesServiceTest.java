package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoexpressedGenesServiceTest {

    @Mock
    CoexpressedGenesDao coexpressedGenesDao;

    CoexpressedGenesService subject;

    static String EX1_GENE_ID = "EX1_GENE_ID";
    static String EX1_GENE_NAME = "example_one_gene";
    
    static String EX2_GENE_ID = "EX2";

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

    @Ignore @Test
    public void test1(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0"), r);
    }

    @Ignore @Test
    public void test2(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0","T2"), r);
    }

    @Ignore
    @Test
    public void test3(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r);
    }

    @Ignore @Test
    public void test4(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02", "T2"), r);
    }

    @Ignore @Test
    public void test5(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T2"), r);
    }

    @Ignore @Test
    public void test6(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0","T1"),
                ImmutableMap.of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1"), r);
    }

    @Ignore @Test
    public void test7(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0","T1"),
                ImmutableMap.of("T0", 1, "T1", 2)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1", "C10", "C11"), r);
    }

    @Ignore @Test
    public void test8(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", 100000)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r);
    }
    @Ignore @Test
    public void test9(){
        ImmutableList<String> r = extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", -1)
        );
        assertEquals(ImmutableList.of("T0"), r);
    }


    public ImmutableList<String> extendGeneQueryWithCoexpressionsWhenGeneQueryTermsAreTheSameAsRetrievedIds
            (ImmutableList<String> geneQueryTerms, Map<String, Integer> requested) {
        GeneQuery g = GeneQuery.create(geneQueryTerms);

        GeneQueryResponse r = new GeneQueryResponse();
        for (String s : geneQueryTerms) {
            r.addGeneIds(s, ImmutableSet.of(s));
        }

        BaselineExperiment e = mock(BaselineExperiment.class);
        when(e.getAccession()).thenReturn(EX1_GENE_ID);

        //GeneQueryResponse response = subject.extendGeneQueryResponseWithCoexpressions(r, e, requested, true);
        return null;

    }
}