package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.HashMap;
import java.util.List;
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

    @Test
    public void test1(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0"), r.terms());
    }

    @Test
    public void test2(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0","T2"), r.terms());
    }

    @Test
    public void test3(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r.terms());
    }

    @Test
    public void test4(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02", "T2"), r.terms());
    }

    @Test
    public void test5(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0", "T2"),
                ImmutableMap.of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T2"), r.terms());
    }

    @Test
    public void test6(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0","T1"),
                ImmutableMap.of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1"), r.terms());
    }

    @Test
    public void test7(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0","T1"),
                ImmutableMap.of("T0", 1, "T1", 2)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1", "C10", "C11"), r.terms());
    }

    @Test
    public void test8(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", 100000)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r.terms());
    }
    @Test
    public void test9(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.of("T0"),
                ImmutableMap.of("T0", -1)
        );
        assertEquals(ImmutableList.of("T0"), r.terms());
    }


    public GeneQuery extendGeneQueryWithCoexpressions(ImmutableList<String> geneQueryTerms, Map<String, Integer>
            requested) {
        GeneQuery g = GeneQuery.create(geneQueryTerms);

        BaselineExperiment e = mock(BaselineExperiment.class);
        when(e.getAccession()).thenReturn(EX1_GENE_ID);
        return subject.extendGeneQueryWithCoexpressions(e, g, requested);
    }
}