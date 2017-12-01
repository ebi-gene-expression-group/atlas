package uk.ac.ebi.atlas.experimentimport;

import org.apache.solr.client.solrj.SolrServerException;
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
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.GxaAnalyticsLoaderFactory;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Spy
    @Inject
    private ExpressionAtlasExperimentChecker experimentCheckerSpy;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private GxaAnalyticsLoaderFactory analyticsLoaderFactory;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private GxaExperimentDao experimentDao;

    @Inject
    private ExperimentCrudFactory experimentCrudFactory;

    private ExperimentCrud subject;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        subject = experimentCrudFactory.create(experimentDao, experimentCheckerSpy, analyticsLoaderFactory);
    }

    public static final String RNASEQ_BASELINE_ACCESSION = "TEST-RNASEQ-BASELINE";

    @After
    public void tryCleanUp() {
        tryDelete(RNASEQ_BASELINE_ACCESSION);
        tryDelete("TEST-RNASEQ-DIFFERENTIAL");
        tryDelete("TEST-MICROARRAY-1COLOUR-MRNA-DIFFERENTIAL");
        tryDelete("TEST-MICROARRAY-2COLOUR-MRNA-DIFFERENTIAL");
        tryDelete("TEST-MICROARRAY-1COLOUR-MICRORNA-DIFFERENTIAL");
        tryDelete("TEST-MICROARRAY-2COLOUR-MRNA-DIFFERENTIAL");
        tryDelete("TEST-PROTEOMICS-BASELINE");
    }

    private boolean tryDelete(String accession) {
        try{
            subject.deleteExperiment(accession);
            return true;
        } catch (ResourceNotFoundException e){
            return false;
        }
    }

    @Test
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);
        subject.deleteExperiment("FOOBAR");
    }

    @Test
    public void importReloadDeleteRnaSeqBaselineExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete(RNASEQ_BASELINE_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(experimentCheckerSpy, times(2)).checkRnaSeqBaselineFiles(RNASEQ_BASELINE_ACCESSION);
    }

    @Test
    public void importReloadDeleteRnaSeqDifferentialExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-RNASEQ-DIFFERENTIAL", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray1ColourMRnaDifferentialExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-MICROARRAY-1COLOUR-MRNA-DIFFERENTIAL", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray2ColourMRnaDifferentialExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-MICROARRAY-2COLOUR-MRNA-DIFFERENTIAL", ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray1ColourMicroRnaDifferentialExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-MICROARRAY-1COLOUR-MICRORNA-DIFFERENTIAL", ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteProteomicsBaselineExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-PROTEOMICS-BASELINE", ExperimentType.PROTEOMICS_BASELINE);
        verify(experimentCheckerSpy, times(2)).checkProteomicsBaselineFiles("TEST-PROTEOMICS-BASELINE");
    }

    @Test
    public void findAllExperimentsFindsPrivateExperiemtns() throws Exception {
        subject.importExperiment(RNASEQ_BASELINE_ACCESSION, true);
        assertThat(
                subject.findAllExperiments(),
                hasItem(hasProperty("experimentAccession", is(RNASEQ_BASELINE_ACCESSION))));
    }

    public void testImportNewImportExistingAndDelete(String experimentAccession, ExperimentType experimentType) throws IOException, SolrServerException {
        importNewExperimentInsertsDB(experimentAccession, experimentType);
        importExistingExperimentUpdatesDB(experimentAccession, experimentType);
        deleteExperimentDeletesDB(experimentAccession, experimentType);
    }

    public void importNewExperimentInsertsDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        assumeThat(experimentCount(experimentAccession), is(0));
        assumeThat(expressionsCount(experimentAccession, experimentType), is(0));
        assumeThat(dataFileHub.getExperimentFiles(experimentAccession).configuration.exists(), is(true));

        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
        if (experimentType.isDifferential()) {
            assertThat(expressionsCount(experimentAccession, experimentType), is(1));
        }
    }

    public void importExistingExperimentUpdatesDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        ExperimentDTO originalExperimentDTO = subject.findExperiment(experimentAccession);
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));

        if (experimentType.isDifferential()) {
            assertThat(expressionsCount(experimentAccession, experimentType), is(1));
        }

        ExperimentDTO newExperimentDTO = subject.findExperiment(experimentAccession);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));
    }

    public void deleteExperimentDeletesDB(String experimentAccession, ExperimentType experimentType) {
        subject.deleteExperiment(experimentAccession);
        assertThat(experimentCount(experimentAccession), is(0));
        assertThat(expressionsCount(experimentAccession, experimentType), is(0));
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int expressionsCount(String accession, ExperimentType experimentType) {
        if (experimentType.isMicroarray()) {
            return jdbcTemplate.queryForObject("select COUNT(*) from MICROARRAY_DIFF_ANALYTICS WHERE EXPERIMENT = ?", Integer.class, accession);
        }
        else { //if (experimentType.isDifferential()) {
            return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_DIFF_ANALYTICS WHERE EXPERIMENT = ?", Integer.class, accession);
        }
    }

}
