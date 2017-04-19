package uk.ac.ebi.atlas.experimentimport;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexingService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class ExperimentCrudIT {

    @Spy
    @Inject
    private ExpressionAtlasExperimentChecker experimentCheckerSpy;

    @Mock
    private ConditionsIndexingService conditionsIndexingService;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    private AnalyticsLoaderFactory analyticsLoaderFactory;

    @Mock
    private ExperimentDesignFileWriterService experimentDesignFileWriterService;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private ExperimentDAO experimentDAO;

    @Inject
    private CondensedSdrfParser condensedSdrfParser;

    @Inject
    private ConfigurationTrader configurationTrader;

    private ExperimentCrud subject;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        subject = new ExperimentCrud(condensedSdrfParser, experimentDesignFileWriterService,
                conditionsIndexingService,
                experimentDAO, experimentCheckerSpy,
                analyticsLoaderFactory, configurationTrader);

    }

    @After
    public void tryCleanUp() {
        tryDelete("TEST-RNASEQ-BASELINE");
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
        testImportNewImportExistingAndDelete("TEST-RNASEQ-BASELINE", ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(experimentCheckerSpy, times(4)).checkBaselineFiles("TEST-RNASEQ-BASELINE");
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
        verify(experimentCheckerSpy, times(4)).checkBaselineFiles("TEST-PROTEOMICS-BASELINE");
    }

    public void testImportNewImportExistingAndDelete(String experimentAccession, ExperimentType experimentType) throws IOException, SolrServerException {
        importNewExperimentInsertsDB(experimentAccession, experimentType);
        verifyConditionsAddedToSolr(experimentAccession, experimentType);
        importExistingExperimentUpdatesDB(experimentAccession, experimentType);
        verifyConditionsAddedToSolr(experimentAccession, experimentType);
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

    public void verifyConditionsAddedToSolr(String experimentAccession, ExperimentType experimentType) throws SolrServerException,
            IOException {
        verify(conditionsIndexingService, atLeastOnce()).indexConditions(eq(experimentAccession), eq(experimentType), Matchers
                .<ExperimentDesign>any());
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
