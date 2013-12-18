package uk.ac.ebi.atlas.experimentloader;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDIT {


    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUDIT.class);

    public static final String EXPERIMENT_ACCESSION = "TEST-CRUD";

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Before
    public void cleanUpFromPreviousRuns() {
        deleteInactiveBaselineExpressions(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadAndDeleteExperiment() throws IOException {
        assertThat("experiment already exists in db", experimentCount(EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline transcripts already exist in db", baselinesTranscriptsCount(EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions already exist in db", baselineExpressionsCount(EXPERIMENT_ACCESSION), is(0));

        subject.loadExperiment(EXPERIMENT_ACCESSION, false);

        assertThat("experiment row not loaded into db", experimentCount(EXPERIMENT_ACCESSION), is(1));
        assertThat("transcripts not loaded into db", baselinesTranscriptsCount(EXPERIMENT_ACCESSION), is(5));
        assertThat("baseline expressions not loaded into db", baselineExpressionsCount(EXPERIMENT_ACCESSION), is(3332));

        subject.deleteExperiment(EXPERIMENT_ACCESSION);

        assertThat("experiment row was not deleted from db", experimentCount(EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline transcripts were not deleted from db", baselinesTranscriptsCount(EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions were not deleted from db", baselineExpressionsCount(EXPERIMENT_ACCESSION), is(0));
    }


    @Test(expected = ResourceNotFoundException.class)
    public void deleteNonexistantExperimentThrowsResourceNotFoundException() throws Exception {
        subject.deleteExperiment("FOOBAR");
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int baselinesTranscriptsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ?", Integer.class, accession);
    }

    private int baselineExpressionsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ? AND ISACTIVE = 'T'", Integer.class, accession);
    }

    private void deleteInactiveBaselineExpressions(String accession) {
        int count = jdbcTemplate.update("delete from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ? AND ISACTIVE = 'F'", accession);
        LOGGER.info(String.format("deleteInactiveBaselineExpressions %s rows deleted",count));
    }

}
