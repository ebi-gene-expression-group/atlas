package uk.ac.ebi.atlas.experimentimport;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.ScxaAnalyticsLoaderFactory;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentCrudIT {

    @Spy
    @Inject
    private SingleCellExperimentChecker experimentCheckerSpy;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private ScxaAnalyticsLoaderFactory analyticsLoaderFactory;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private ScxaExperimentDao experimentDao;

    @Inject
    private ExperimentCrudFactory experimentCrudFactory;

    private ExperimentCrud subject;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        subject = experimentCrudFactory.create(experimentDao, experimentCheckerSpy, analyticsLoaderFactory);
    }

    public static final String SINGLE_CELL_ACCESSION = "TEST-SC";

    @After
    public void tryCleanUp() {
        tryDelete(SINGLE_CELL_ACCESSION);
    }

    private boolean tryDelete(String accession) {
        try{
            subject.deleteExperiment(accession);
            return true;
        } catch (ResourceNotFoundException e){
            return false;
        }
    }

    @Test(expected=ResourceNotFoundException.class)
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        subject.deleteExperiment("FOOBAR");
    }

    @Test
    public void importReloadDeleteExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete(
                SINGLE_CELL_ACCESSION, ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE);
        verify(experimentCheckerSpy, times(2))
                .checkAllFiles(SINGLE_CELL_ACCESSION, ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE);
    }

    @Test
    public void findAllExperimentsFindsPrivateExperiemtns() throws Exception {
        subject.importExperiment(SINGLE_CELL_ACCESSION, true);
        assertThat(
                subject.findAllExperiments(),
                hasItem(hasProperty("experimentAccession", is(SINGLE_CELL_ACCESSION))));
    }

    public void testImportNewImportExistingAndDelete(String experimentAccession, ExperimentType experimentType) throws IOException, SolrServerException {
        importNewExperimentInsertsDB(experimentAccession, experimentType);
        importExistingExperimentUpdatesDB(experimentAccession, experimentType);
        deleteExperimentDeletesDB(experimentAccession);
    }

    public void importNewExperimentInsertsDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        assumeThat(experimentCount(experimentAccession), is(0));
        assumeThat(expressionsCount(experimentAccession), is(0));
        assumeThat(dataFileHub.getExperimentFiles(experimentAccession).configuration.exists(), is(true));

        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
    }

    public void importExistingExperimentUpdatesDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        ExperimentDTO originalExperimentDTO = subject.findExperiment(experimentAccession);
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));

        if (experimentType.isDifferential()) {
            assertThat(expressionsCount(experimentAccession), is(1));
        }

        ExperimentDTO newExperimentDTO = subject.findExperiment(experimentAccession);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));
    }

    public void deleteExperimentDeletesDB(String experimentAccession) {
        subject.deleteExperiment(experimentAccession);
        assertThat(experimentCount(experimentAccession), is(0));
        assertThat(expressionsCount(experimentAccession), is(0));
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) from scxa_experiment WHERE accession=?", Integer.class, accession);
    }

    private int expressionsCount(String accession) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) from scxa_analytics WHERE experiment_accession=?", Integer.class, accession);
    }

}
