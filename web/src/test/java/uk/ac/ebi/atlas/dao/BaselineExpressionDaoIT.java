package uk.ac.ebi.atlas.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDto;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDtoInputStream;

import javax.inject.Inject;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
@Transactional  // enable transaction manager, so that changes to the database are rolled back after each test method
public class BaselineExpressionDaoIT {

    public static final String EXPERIMENT_ACCESSION = "delme";

    @Inject
    private BaselineExpressionDao baselineExpressionDao;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private BaselineExpressionDtoInputStream baselineExpressionDtoInputStream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(baselineExpressionDtoInputStream.readNext()).thenReturn(new BaselineExpressionDto("gene1", "g1", 1.0), new BaselineExpressionDto("gene2", "g1", 1.0), null);
    }

    @Test
    public void insertAndDeleteBaselineExpressions() throws IOException {
        assertThat(getCount(), is(0));

        baselineExpressionDao.loadBaselineExpressions(EXPERIMENT_ACCESSION, baselineExpressionDtoInputStream);

        assertThat(getCount(), is(2));

        baselineExpressionDao.deleteBaselineExpressions(EXPERIMENT_ACCESSION);

        assertThat(getCount(), is(0));

        verify(baselineExpressionDtoInputStream).close();
    }

    private int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RNASEQ_BSLN_EXPRESSIONS where ISACTIVE='T' AND experiment = ?", Integer.class, EXPERIMENT_ACCESSION);
    }


}
