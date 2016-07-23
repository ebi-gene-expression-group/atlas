package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import oracle.sql.ARRAY;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaselineExperimentAssayGroupsDAOTest {


    @Mock
     JdbcTemplate jdbcTemplate;

    @Mock
     OracleObjectFactory oracleObjectFactory;

    private BaselineExperimentAssayGroupsDao subject;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(oracleObjectFactory.createOracleArrayForIdentifiers(anyCollection())).thenReturn(mock(ARRAY
                .class));
        when(oracleObjectFactory.createOracleArrayForIndexedAssayGroup(anyCollection())).thenReturn(mock(ARRAY
                .class));

        subject = new BaselineExperimentAssayGroupsDao(jdbcTemplate,oracleObjectFactory);
    }

    @Test
    public void queryWithSomeReallyGoodSql(){
        IndexedAssayGroup E_MTAB_599_g6 = new IndexedAssayGroup("E-MTAB-599", "g6");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        SetMultimap<String, String> expected = ImmutableSetMultimap.of("k","v");

        Mockito.when(jdbcTemplate.query(captor.capture(), any(Object [] .class),  any(ResultSetExtractor.class)))
                .thenReturn(expected);



        SetMultimap<String,String> result = subject.fetchExperimentAssayGroupsWithNonSpecificExpression(ImmutableList.of(E_MTAB_599_g6), ImmutableList.<String>of());

        assertEquals(expected,result);

        String sql = captor.getValue();
        assertTrue(Pattern.matches("SELECT.*", sql));


    }



}
