package uk.ac.ebi.atlas.experimentimport;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ExperimentCrudIT {
    private static final String RNASEQ_BASELINE_ACCESSION = "E-ERAD-475";
    private static final String RNASEQ_DIFFERENTIAL = "E-MTAB-5633";
    private static final String PROTEOMICS_BASELINE_ACCESSION = "E-PROT-1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Spy
    @Inject
    private ExpressionAtlasExperimentChecker experimentCheckerSpy;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private GxaExperimentDao experimentDao;

    @Inject
    private ExperimentCrudFactory experimentCrudFactory;

    private ExperimentCrud subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = experimentCrudFactory.create(experimentDao, experimentCheckerSpy);
    }

    @After
    public void tryCleanUp() {
        tryDelete(RNASEQ_BASELINE_ACCESSION);
        tryDelete(RNASEQ_DIFFERENTIAL);
        tryDelete(PROTEOMICS_BASELINE_ACCESSION);
    }

    private boolean tryDelete(String accession) {
        try {
            subject.deleteExperiment(accession);
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    @Test
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);
        subject.deleteExperiment("FOOBAR");
    }

    @Test
    public void importReloadDeleteRnaSeqBaselineExperiment() throws IOException {
        testImportNewImportExistingAndDelete(RNASEQ_BASELINE_ACCESSION);
        verify(experimentCheckerSpy, times(2)).checkRnaSeqBaselineFiles(RNASEQ_BASELINE_ACCESSION);
    }

    @Test
    public void importReloadDeleteRnaSeqDifferentialExperiment() throws IOException {
        testImportNewImportExistingAndDelete(RNASEQ_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteProteomicsBaselineExperiment() throws IOException {
        testImportNewImportExistingAndDelete(PROTEOMICS_BASELINE_ACCESSION);

        verify(experimentCheckerSpy, times(2)).checkProteomicsBaselineFiles(PROTEOMICS_BASELINE_ACCESSION);
    }

    @Test
    public void
    findAllExperimentsFindsPrivateExperiments() throws Exception {
        subject.importExperiment(RNASEQ_BASELINE_ACCESSION, true);
        assertThat(
                subject.findAllExperiments(),
                hasItem(hasProperty("experimentAccession", is(RNASEQ_BASELINE_ACCESSION))));
    }

    private void
    testImportNewImportExistingAndDelete(String experimentAccession) throws IOException {
        importNewExperimentInsertsDB(experimentAccession);
        importExistingExperimentUpdatesDB(experimentAccession);
        deleteExperimentDeletesDB(experimentAccession);
    }

    private void
    importNewExperimentInsertsDB(String experimentAccession) throws IOException {
        assumeThat(experimentCount(experimentAccession), is(0));
        assumeThat(dataFileHub.getExperimentFiles(experimentAccession).configuration.exists(), is(true));

        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
    }

    private void
    importExistingExperimentUpdatesDB(String experimentAccession) throws IOException {
        ExperimentDTO originalExperimentDTO = subject.findExperiment(experimentAccession);
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));

        ExperimentDTO newExperimentDTO = subject.findExperiment(experimentAccession);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));
    }

    private void deleteExperimentDeletesDB(String experimentAccession) {
        subject.deleteExperiment(experimentAccession);
        assertThat(experimentCount(experimentAccession), is(0));
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM experiment WHERE accession = ?", Integer.class, accession);
    }

}
