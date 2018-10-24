package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaselineExperimentsCacheLoaderIT {
    private static final String E_MTAB_3827_ACCESSION = "E-MTAB-3827";

    @Inject
    private DataSource dataSource;
    @Inject
    private RnaSeqBaselineExperimentFactory rnaSeqBaselineExperimentFactory;

    @Mock
    private GxaExperimentDao expressionAtlasExperimentDao;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    @Inject
    private IdfParser idfParser;

    private ExperimentsCacheLoader<BaselineExperiment> subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ExperimentDTO experimentDTO = new ExperimentDTO(
                E_MTAB_3827_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE,
                "Homo sapiens",
                Collections.emptySet(),
                Collections.emptySet(),
                "title", new Date(),
                false,
                UUID.randomUUID().toString());
        when(expressionAtlasExperimentDao.getExperimentAsAdmin(E_MTAB_3827_ACCESSION)).thenReturn(experimentDTO);

        subject =
                new ExperimentsCacheLoader<>(
                        experimentDesignParser, expressionAtlasExperimentDao, rnaSeqBaselineExperimentFactory, idfParser);
    }


    @Test
    void correctSpeciesReadFromDatabase() {
        BaselineExperiment experiment = subject.load(E_MTAB_3827_ACCESSION);
        String species = experiment.getSpecies().getName();
        assertThat(species).isEqualTo("Homo sapiens");
    }


    @Test
    void experimentShouldOnlyContainRunsFromDataFile() {
        BaselineExperiment experiment = subject.load(E_MTAB_3827_ACCESSION);

        assertThat(experiment.getAnalysedAssays())
                .contains("ERR232403", "ERR232404", "ERR855840", "ERR353365", "ERR244134")
                .hasSize(66);
    }

    @Test
    void experimentShouldContainAssayGroups() {
        BaselineExperiment experiment = subject.load(E_MTAB_3827_ACCESSION);
        assertThat(experiment.getDataColumnDescriptors()).hasSize(27);
    }

    @Test
    void loadNonExistentExperimentThrowsWrappedFileNotFoundException() {
        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.load("FOOBAR"))
                .withCauseInstanceOf(FileNotFoundException.class);
    }

}
