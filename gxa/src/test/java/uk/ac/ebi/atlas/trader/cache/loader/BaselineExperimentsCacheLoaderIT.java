package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class BaselineExperimentsCacheLoaderIT {

    private String accession = "E-MTAB-513";

    @Inject
    private RnaSeqBaselineExperimentFactory rnaSeqBaselineExperimentFactory;

    @Mock
    private GxaExperimentDao expressionAtlasExperimentDao;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    private ExperimentsCacheLoader<BaselineExperiment> subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ExperimentDTO experimentDTO = new ExperimentDTO(
                accession, ExperimentType.RNASEQ_MRNA_BASELINE,
                "Homo sapiens",
                Collections.emptySet(),
                Collections.emptySet(),
                "title", new Date(),
                false,
                UUID.randomUUID().toString());
        when(expressionAtlasExperimentDao.getExperimentAsAdmin(accession)).thenReturn(experimentDTO);

        subject =
                new ExperimentsCacheLoader<>(
                        experimentDesignParser, expressionAtlasExperimentDao, rnaSeqBaselineExperimentFactory);
    }


    @Test
    public void correctSpeciesReadFromDatabase() {
        BaselineExperiment experiment = subject.load(accession);
        String species = experiment.getSpecies().getName();
        assertThat(species).isEqualTo("Homo sapiens");
    }


    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() {
        BaselineExperiment experiment = subject.load(accession);

        assertThat(experiment.getAnalysedAssays())
                .containsExactlyInAnyOrder(
                        "ERR030872", "ERR030873", "ERR030874", "ERR030875", "ERR030876", "ERR030877", "ERR030878",
                        "ERR030879", "ERR030880", "ERR030881", "ERR030882", "ERR030883", "ERR030884", "ERR030885",
                        "ERR030886", "ERR030887");
    }

    @Test
    public void experimentShouldContainAssayGroups() {
        BaselineExperiment experiment = subject.load(accession);
        assertThat(experiment.getDataColumnDescriptors()).hasSize(16);
    }

    @Test
    public void loadNonExistentExperimentThrowsWrappedFileNotFoundException() {
        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.load("FOOBAR"))
                .withCauseInstanceOf(FileNotFoundException.class);
    }

}
