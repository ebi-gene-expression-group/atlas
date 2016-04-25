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

    static String EX1 = "EX1";

    static Map<Pair<String,String>, ImmutableList<String>> stateOfDatabase = new HashMap<>();
    static {
        stateOfDatabase.put(Pair.of(EX1, "T0"), ImmutableList.<String>of("C00", "C01", "C02"));
        stateOfDatabase.put(Pair.of(EX1, "T1"), ImmutableList.<String>of("C10", "C11"));
        stateOfDatabase.put(Pair.of("EX2", "T1"), ImmutableList.<String>of("C12"));
    }

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        subject = new CoexpressedGenesService(coexpressedGenesDao);
        for(Map.Entry<Pair<String,String>, ImmutableList<String>> e: stateOfDatabase.entrySet()){
            when(coexpressedGenesDao.coexpressedGenesFor(e.getKey().getLeft(), e.getKey().getRight())).thenReturn(e
                    .getValue());
        }
    }

    @Test
    public void test1(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0"), r.terms());
    }

    @Test
    public void test2(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0", "T2"),
                ImmutableMap.<String, Integer>of()
        );
        assertEquals(ImmutableList.of("T0","T2"), r.terms());
    }

    @Test
    public void test3(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0"),
                ImmutableMap.<String, Integer>of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r.terms());
    }

    @Test
    public void test4(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0", "T2"),
                ImmutableMap.<String, Integer>of("T0", 3)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02", "T2"), r.terms());
    }

    @Test
    public void test5(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0", "T2"),
                ImmutableMap.<String, Integer>of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T2"), r.terms());
    }

    @Test
    public void test6(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0","T1"),
                ImmutableMap.<String, Integer>of("T0", 1)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1"), r.terms());
    }

    @Test
    public void test7(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0","T1"),
                ImmutableMap.<String, Integer>of("T0", 1, "T1", 2)
        );
        assertEquals(ImmutableList.of("T0","C00", "T1", "C10", "C11"), r.terms());
    }

    @Test
    public void test8(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0"),
                ImmutableMap.<String, Integer>of("T0", 100000)
        );
        assertEquals(ImmutableList.of("T0","C00", "C01", "C02"), r.terms());
    }
    @Test
    public void test9(){
        GeneQuery r = extendGeneQueryWithCoexpressions(
                ImmutableList.<String>of("T0"),
                ImmutableMap.<String, Integer>of("T0", -1)
        );
        assertEquals(ImmutableList.of("T0"), r.terms());
    }


    public GeneQuery extendGeneQueryWithCoexpressions(ImmutableList<String> geneQueryTerms, Map<String, Integer>
            requested) {
        GeneQuery g = GeneQuery.create(geneQueryTerms);

        BaselineExperiment e = mock(BaselineExperiment.class);
        when(e.getAccession()).thenReturn(EX1);
        return subject.extendGeneQueryWithCoexpressions(e, g, requested);
    }
}