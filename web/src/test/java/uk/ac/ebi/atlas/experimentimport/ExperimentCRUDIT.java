package uk.ac.ebi.atlas.experimentimport;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsIndex;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDIT {

    @Value("#{configuration['experiment.data.location']}")
    String experimentDataLocation;

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private ExperimentMetadataCRUD experimentMetadataCRUD;

    @Mock
    private SolrClient solrClientMock;

    @Captor
    ArgumentCaptor<Collection<Condition>> collectionConditionCaptor;

    // Used to check the DB
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        BaselineConditionsIndex baselineConditionIndex = new BaselineConditionsIndex(solrClientMock, new BaselineConditionsBuilder());
        DifferentialConditionsIndex differentialConditionIndex = new DifferentialConditionsIndex(solrClientMock, new DifferentialConditionsBuilder());
        ConditionsIndexTrader conditionsIndexTrader = new ConditionsIndexTrader(baselineConditionIndex, differentialConditionIndex);
        experimentMetadataCRUD.setConditionsIndexTrader(conditionsIndexTrader);

        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);
    }

    @Test
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);
        subject.deleteExperiment("FOOBAR");
    }

    @Test
    public void importReloadDeleteRnaSeqBaselineExperiment() throws IOException, SolrServerException {
        testImportNewImportExistingAndDelete("TEST-RNASEQ-BASELINE", ExperimentType.RNASEQ_MRNA_BASELINE);
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
    }


    public void testImportNewImportExistingAndDelete(String experimentAccession, ExperimentType experimentType) throws IOException, SolrServerException {
        importNewExperimentInsertsDB(experimentAccession, experimentType);
        verifyConditionsAddedToSolr(experimentAccession);
        importExistingExperimentUpdatesDB(experimentAccession, experimentType);
        verifyConditionsAddedToSolr(experimentAccession);
        deleteExperimentDeletesDB(experimentAccession, experimentType);
    }

    public void importNewExperimentInsertsDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        assumeThat(experimentCount(experimentAccession), is(0));
        assumeThat(expressionsCount(experimentAccession, experimentType), is(0));
        assumeThat(new File(experimentDataLocation, experimentAccession).exists(), is(true));

        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
        assertThat(expressionsCount(experimentAccession, experimentType), is(1));
    }

    public void importExistingExperimentUpdatesDB(String experimentAccession, ExperimentType experimentType) throws IOException {
        ExperimentDTO originalExperimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
        assertThat(expressionsCount(experimentAccession, experimentType), is(1));

        ExperimentDTO newExperimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));
    }

    public void verifyConditionsAddedToSolr(String experimentAccession) throws SolrServerException, IOException {
        verify(solrClientMock).addBeans(collectionConditionCaptor.capture());

        Collection<Condition> beans = collectionConditionCaptor.getValue();

        assertThat(beans, hasSize(greaterThan(0)));
        assertThat(beans.iterator().next().getExperimentAccession(), is(experimentAccession));

        reset(solrClientMock);
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
        if (experimentType.isBaseline()) {
            return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ?", Integer.class, accession);
        }
        else if (experimentType.isMicroarray()) {
            return jdbcTemplate.queryForObject("select COUNT(*) from MICROARRAY_DIFF_ANALYTICS WHERE EXPERIMENT = ?", Integer.class, accession);
        }
        else { //if (experimentType.isDifferential()) {
            return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_DIFF_ANALYTICS WHERE EXPERIMENT = ?", Integer.class, accession);
        }
    }

}
