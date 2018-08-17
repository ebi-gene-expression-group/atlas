package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao.CE_GENES_SQL_QUERY_TEMPLATE;

@RunWith(MockitoJUnitRunner.class)
public class CoexpressedGenesDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    private CoexpressedGenesDao subject;

    @Before
    public void setUp() {
        subject = new CoexpressedGenesDao(jdbcTemplate);
    }

    @Test
    public void noResultsReturnsEmptyList() {
        // This is a Spring thing, see JdbcTemplate L818 and L795, DataAccessUtils L68
        when(jdbcTemplate.queryForObject(CE_GENES_SQL_QUERY_TEMPLATE, String.class, "E-FOOBAR", "ENSGFOOBAR"))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThat(subject.coexpressedGenesFor("E-FOOBAR", "ENSGFOOBAR")).isEmpty();
    }

    @Test
    public void originalGeneIdIsFilteredOut() {
        when(jdbcTemplate.queryForObject(CE_GENES_SQL_QUERY_TEMPLATE, String.class, "E-FOOBAR", "ENSGFOOBAR"))
                .thenReturn("ENSGFOOBAR, ENSGFOOBAR1, ENSGFOOBAR2, ENSGFOOBAR3");

        assertThat(subject.coexpressedGenesFor("E-FOOBAR", "ENSGFOOBAR"))
                .hasSize(3)
                .doesNotContain("ENSGFOOBAR");
    }
}
