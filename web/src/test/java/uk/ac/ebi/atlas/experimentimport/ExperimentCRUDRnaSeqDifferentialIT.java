package uk.ac.ebi.atlas.experimentimport;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDRnaSeqDifferentialIT {


    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUDRnaSeqDifferentialIT.class);

    public static final String NEW_EXPERIMENT_ACCESSION = "TEST-RNASEQ-DIFF";

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Before
    public void cleanUpBefore() {
        deleteInactiveAnalytics();
    }

    @After
    public void cleanUpAfter() {
        deleteInactiveAnalytics();
    }

    @Test
    public void loadAndDeleteNewExperiment() throws IOException {
        assertThat("experiment already exists in db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("analytics already exist in db", analyticsCount(NEW_EXPERIMENT_ACCESSION), is(0));

        subject.importExperiment(NEW_EXPERIMENT_ACCESSION, false);

        assertThat("experiment row not loaded into db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(1));
        assertThat("analytics not loaded into db", analyticsCount(NEW_EXPERIMENT_ACCESSION), is(18));

        subject.deleteExperiment(NEW_EXPERIMENT_ACCESSION);

        assertThat("experiment row was not deleted from db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("analytics were not deleted from db", analyticsCount(NEW_EXPERIMENT_ACCESSION), is(0));
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int analyticsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_DIFF_ANALYTICS WHERE EXPERIMENT = ? AND ISACTIVE = 'T'", Integer.class, accession);
    }

    private void deleteInactiveAnalytics() {
        int count = jdbcTemplate.update("delete from RNASEQ_DIFF_ANALYTICS WHERE ISACTIVE = 'F'");
        LOGGER.info(String.format("deleteInactiveAnalytics %s rows deleted",count));
    }

}
