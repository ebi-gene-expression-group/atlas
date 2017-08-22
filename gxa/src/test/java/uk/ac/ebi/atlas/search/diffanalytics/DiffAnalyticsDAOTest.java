package uk.ac.ebi.atlas.search.diffanalytics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class DiffAnalyticsDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    DiffAnalyticsRowMapper dbeRowMapper;

    DiffAnalyticsDao subject;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.subject = new DiffAnalyticsDao(jdbcTemplate,dbeRowMapper);
    }

    @Test
    public void weQueryWithSomeReallyGoodSql(){
        String geneId = "geneId";

        String v = subject.buildSelect(geneId).getQuery();

        assertTrue(Pattern.matches("SELECT.*FROM.*", v.toUpperCase()));
        assertTrue(v.toUpperCase().contains("PRIVATE = 'F'"));
    }

}
