package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.collect.Lists;
import oracle.sql.ARRAY;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiffAnalyticsDAOTest {


    @Mock
    JdbcTemplate jdbcTemplate;


    @Mock
    DiffAnalyticsRowMapper dbeRowMapper;

    @Mock
    OracleObjectFactory oracleObjectFactory;


    DiffAnalyticsDao subject;

    @Before
            public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.subject = new DiffAnalyticsDao(jdbcTemplate,dbeRowMapper,oracleObjectFactory);

        when(oracleObjectFactory.createOracleArrayForIdentifiers(anyCollection())).thenReturn(mock(ARRAY
                .class));
        when(oracleObjectFactory.createOracleArrayForIndexedAssayGroup(anyCollection())).thenReturn(mock(ARRAY
                .class));
    }

    @Test
    public void weQueryWithSomeReallyGoodSql(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.when(jdbcTemplate.query(captor.capture(), eq(dbeRowMapper),  any())).thenReturn(new ArrayList<DiffAnalytics>());

        IndexedAssayGroup indexedContrast1 = new IndexedAssayGroup("E-MTAB-1066", "g2_g3");

        Collection<IndexedAssayGroup> contrasts = Lists.newArrayList(indexedContrast1);
        Collection<String> geneIds = new HashSet<>();

        String species = "";

        List<DiffAnalytics> expressions = subject.fetchTopExpressions(contrasts, geneIds, species);

        String v = captor.getValue();

        assertTrue(Pattern.matches("SELECT.*FROM.*", v.toUpperCase()));
        assertTrue(v.toUpperCase().contains("PRIVATE = 'F'"));
    }

}
