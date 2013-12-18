package uk.ac.ebi.atlas.experimentloader;

import org.apache.log4j.Logger;
import org.junit.After;
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

    public static final String NEW_EXPERIMENT_ACCESSION = "TEST-CRUD";
    public static final String EXISTING_EXPERIMENT_ACCESSION = "E-MTAB-599";

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @After
    public void cleanUp() {
        deleteInactiveBaselineExpressions();
    }

    @Test
    public void loadAndDeleteNewExperiment() throws IOException {
        assertThat("experiment already exists in db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline transcripts already exist in db", baselinesTranscriptsCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions already exist in db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));

        subject.loadExperiment(NEW_EXPERIMENT_ACCESSION, false);

        assertThat("experiment row not loaded into db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(1));
        assertThat("transcripts not loaded into db", baselinesTranscriptsCount(NEW_EXPERIMENT_ACCESSION), is(5));
        assertThat("baseline expressions not loaded into db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(3332));

        subject.deleteExperiment(NEW_EXPERIMENT_ACCESSION);

        assertThat("experiment row was not deleted from db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline transcripts were not deleted from db", baselinesTranscriptsCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions were not deleted from db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));
    }

    @Test
    public void reloadExistingExperiment() throws IOException {
        assertThat("experiment does not already exist in db", experimentCount(EXISTING_EXPERIMENT_ACCESSION), is(1));
        assertThat("baseline transcripts do not already exist in db", baselinesTranscriptsCount(EXISTING_EXPERIMENT_ACCESSION), is(3));
        assertThat("baseline expressions do not already exist in db", baselineExpressionsCount(EXISTING_EXPERIMENT_ACCESSION), is(20000));

        subject.loadExperiment(EXISTING_EXPERIMENT_ACCESSION, false);

        assertThat("count of experiment rows has changed", experimentCount(EXISTING_EXPERIMENT_ACCESSION), is(1));
        assertThat("count of transcripts has changed", baselinesTranscriptsCount(EXISTING_EXPERIMENT_ACCESSION), is(3));
        assertThat("count of baseline expressions has changed", baselineExpressionsCount(EXISTING_EXPERIMENT_ACCESSION), is(20000));
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

    private void deleteInactiveBaselineExpressions() {
        int count = jdbcTemplate.update("delete from RNASEQ_BSLN_EXPRESSIONS WHERE ISACTIVE = 'F'");
        LOGGER.info(String.format("deleteInactiveBaselineExpressions %s rows deleted",count));
    }

}
