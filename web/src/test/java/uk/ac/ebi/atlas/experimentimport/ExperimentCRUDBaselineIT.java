package uk.ac.ebi.atlas.experimentimport;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDBaselineIT {


    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUDBaselineIT.class);

    public static final String NEW_EXPERIMENT_ACCESSION = "TEST-BASELINE";
    public static final String EXISTING_EXPERIMENT_ACCESSION = "E-MTAB-599";

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private ExperimentMetadataCRUD experimentMetadataCRUD;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AnalyticsIndexerManager analyticsIndexerManagerMock;

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
        MockitoAnnotations.initMocks(this);

        doNothing().when(analyticsIndexerManagerMock).deleteFromAnalyticsIndex(NEW_EXPERIMENT_ACCESSION);

        experimentMetadataCRUD.setAnalyticsIndexerManager(analyticsIndexerManagerMock);
        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);

        assertThat("experiment already exists in db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions already exist in db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));

        subject.importExperiment(NEW_EXPERIMENT_ACCESSION, false);

        assertThat("experiment row not loaded into db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(1));
        assertThat("baseline expressions not loaded into db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(3332));

        subject.deleteExperiment(NEW_EXPERIMENT_ACCESSION);

        assertThat("experiment row was not deleted from db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions were not deleted from db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));
    }

    @Test
    public void reloadExistingExperiment() throws IOException {
        ExperimentDTO originalExperimentDTO = experimentMetadataCRUD.findExperiment(EXISTING_EXPERIMENT_ACCESSION);

        assertThat("experiment does not already exist in db", experimentCount(EXISTING_EXPERIMENT_ACCESSION), is(1));
        assertThat("baseline expressions do not already exist in db", baselineExpressionsCount(EXISTING_EXPERIMENT_ACCESSION), is(124394));

        subject.importExperiment(EXISTING_EXPERIMENT_ACCESSION, false);

        assertThat("count of experiment rows has changed", experimentCount(EXISTING_EXPERIMENT_ACCESSION), is(1));
        assertThat("count of baseline expressions has changed", baselineExpressionsCount(EXISTING_EXPERIMENT_ACCESSION), is(124394));

        ExperimentDTO newExperimentDTO = experimentMetadataCRUD.findExperiment(EXISTING_EXPERIMENT_ACCESSION);

        assertThat("access key has changed", originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() throws Exception {
        subject.deleteExperiment("FOOBAR");
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int baselineExpressionsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ? AND ISACTIVE = 'T'", Integer.class, accession);
    }

    private void deleteInactiveAnalytics() {
        int count = jdbcTemplate.update("delete from RNASEQ_BSLN_EXPRESSIONS WHERE ISACTIVE = 'F'");
        LOGGER.info(String.format("RNASEQ_BSLN_EXPRESSIONS %s rows deleted",count));
    }

}
