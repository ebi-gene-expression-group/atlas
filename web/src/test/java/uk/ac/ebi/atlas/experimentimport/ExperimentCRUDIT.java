package uk.ac.ebi.atlas.experimentimport;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;

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
        doNothing().when(analyticsIndexerManagerMock).deleteFromAnalyticsIndex(anyString());
    }

    @Test
    public void importReloadDeleteRnaSeqBaselineExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-RNASEQ-BASELINE", ExperimentType.RNASEQ_MRNA_BASELINE);
    }

    @Test
    public void importReloadDeleteRnaSeqDifferentialExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-RNASEQ-DIFFERENTIAL", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray1ColourMRnaDifferentialExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-MICROARRAY-1COLOUR-MRNA-DIFFERENTIAL", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray2ColourMRnaDifferentialExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-MICROARRAY-2COLOUR-MRNA-DIFFERENTIAL", ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteMicroarray1ColourMicroRnaDifferentialExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-MICROARRAY-1COLOUR-MICRORNA-DIFFERENTIAL", ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
    }

    @Test
    public void importReloadDeleteProteomicsBaselineExperiment() throws IOException {
        loadReloadDeleteExperiment("TEST-PROTEOMICS-BASELINE", ExperimentType.PROTEOMICS_BASELINE);
    }


    public void loadReloadDeleteExperiment(String experimentAccession, ExperimentType experimentType) throws IOException {
        assumeThat(experimentCount(experimentAccession), is(0));
        assumeThat(expressionsCount(experimentAccession, experimentType), is(0));
        assumeThat(new File(experimentDataLocation, experimentAccession).exists(), is(true));

        //load
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
        assertThat(expressionsCount(experimentAccession, experimentType), is(1));

        //reload
        ExperimentDTO originalExperimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);
        subject.importExperiment(experimentAccession, false);
        assertThat(experimentCount(experimentAccession), is(1));
        assertThat(expressionsCount(experimentAccession, experimentType), is(1));

        ExperimentDTO newExperimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);
        assertThat(originalExperimentDTO.getAccessKey(), is(newExperimentDTO.getAccessKey()));

        //delete
        subject.deleteExperiment(experimentAccession);
        assertThat(experimentCount(experimentAccession), is(0));
        assertThat(expressionsCount(experimentAccession, experimentType), is(0));
    }

    @Test
    public void deleteNonExistentExperimentThrowsResourceNotFoundException() {
        thrown.expect(ResourceNotFoundException.class);

        subject.deleteExperiment("FOOBAR");
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
