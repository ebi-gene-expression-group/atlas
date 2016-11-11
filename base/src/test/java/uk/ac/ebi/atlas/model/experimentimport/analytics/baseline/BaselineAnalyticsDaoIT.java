package uk.ac.ebi.atlas.model.experimentimport.analytics.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsDAO;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.RnaSeqBaselineAnalyticsInputStream;

import javax.inject.Inject;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
@Transactional  // enable transaction manager, so that changes to the database are rolled back after each test method
public class BaselineAnalyticsDaoIT {

    public static final String EXPERIMENT_ACCESSION = "delme";

    @Inject
    private BaselineAnalyticsDAO baselineAnalyticsDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RnaSeqBaselineAnalyticsInputStream rnaSeqBaselineAnalyticsInputStream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(rnaSeqBaselineAnalyticsInputStream.readNext()).thenReturn(new BaselineAnalytics("gene1", "g1", 1.0), new BaselineAnalytics("gene2", "g1", 1.0), null);
    }

    @Test
    public void insertAndDeleteBaselineExpressions() throws IOException {
        assertThat(getCount(), is(0));

        baselineAnalyticsDAO.loadAnalytics(EXPERIMENT_ACCESSION, rnaSeqBaselineAnalyticsInputStream);
        assertThat(getCount(), is(2));

        baselineAnalyticsDAO.deleteAnalytics(EXPERIMENT_ACCESSION);
        assertThat(getCount(), is(0));
        verify(rnaSeqBaselineAnalyticsInputStream).close();
    }

    private int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RNASEQ_BSLN_EXPRESSIONS where experiment = ?", Integer.class, EXPERIMENT_ACCESSION);
    }

}
