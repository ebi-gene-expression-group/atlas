package uk.ac.ebi.atlas.experimentimport;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDBaselineIT {

    private static final String NEW_EXPERIMENT_ACCESSION = "TEST-BASELINE";

    @Value("#{configuration['experiment.data.location']}")
    String experimentDataLocation;

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private ExperimentMetadataCRUD experimentMetadataCRUD;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AnalyticsIndexerManager analyticsIndexerManagerMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        experimentMetadataCRUD.setAnalyticsIndexerManager(analyticsIndexerManagerMock);
        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);
        doNothing().when(analyticsIndexerManagerMock).deleteFromAnalyticsIndex(NEW_EXPERIMENT_ACCESSION);
    }

    @Test
    public void loadReloadDeleteNewExperiment() throws IOException {
        assumeThat(experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assumeThat(baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assumeThat(new File(experimentDataLocation, NEW_EXPERIMENT_ACCESSION).exists(), is(true));

        //load
        subject.importExperiment(NEW_EXPERIMENT_ACCESSION, false);
        assertThat(experimentCount(NEW_EXPERIMENT_ACCESSION), is(1));
        assertThat(baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(1));

        //reload
        ExperimentDTO originalExperimentDTO = experimentMetadataCRUD.findExperiment(NEW_EXPERIMENT_ACCESSION);
        subject.importExperiment(NEW_EXPERIMENT_ACCESSION, false);
        assertThat(experimentCount(NEW_EXPERIMENT_ACCESSION), is(1));
        assertThat(baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(1));

        ExperimentDTO newExperimentDTO = experimentMetadataCRUD.findExperiment(NEW_EXPERIMENT_ACCESSION);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));

        //delete
        subject.deleteExperiment(NEW_EXPERIMENT_ACCESSION);
        assertThat(experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat(baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));
    }

    @Test
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);

        subject.deleteExperiment("FOOBAR");
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int baselineExpressionsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ?", Integer.class, accession);
    }

}
