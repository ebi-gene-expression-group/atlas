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
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DifferentialExperimentsCacheLoaderIT {
    private static final String EXPERIMENT_ACCESSION = "E-MAXD-6";
    private String species = "Drosophila melanogaster";

    @Inject
    private DataSource dataSource;

    @Inject
    private DifferentialExperimentFactory differentialExperimentFactory;

    @Mock
    private GxaExperimentDao expressionAtlasExperimentDao;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    @Inject
    private IdfParser idfParser;

    private ExperimentsCacheLoader<DifferentialExperiment> subject;

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
        ExperimentDTO experimentDTO = new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                species, Collections.emptySet(), Collections.emptySet(), "title", new Date(),
                false, UUID.randomUUID().toString());
        when(expressionAtlasExperimentDao.getExperimentAsAdmin(EXPERIMENT_ACCESSION)).thenReturn(experimentDTO);

        subject =
                new ExperimentsCacheLoader<>(
                        experimentDesignParser, expressionAtlasExperimentDao, differentialExperimentFactory, idfParser);
    }

    @Test
    void shouldHaveExactlyOneSpecies() {
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        assertThat(experiment.getSpecies().getName(), is(species));
    }

    @Test
    void shouldContainNineContrasts() {
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        assertThat(experiment.getDataColumnDescriptors().size(), is(9));
    }

    @Test
    void shouldContainGivenContrast() {
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        Contrast contrast = experiment.getDataColumnDescriptor("g14_g13");

        assertThat(contrast.getId(), is("g14_g13"));
        assertThat(contrast.getDisplayName(), startsWith("'Asobara tabida'"));

        AssayGroup expectedReferenceAssayGroup =
                AssayGroupFactory.create(
                        "g14",
                        "NERC_EG_Wertheim_Hybridisation_10mminusbyb_87",
                        "NERC_EG_Wertheim_Hybridisation_10mminuseyb_75",
                        "NERC_EG_Wertheim_Hybridisation_10mminusayb_76",
                        "NERC_EG_Wertheim_Hybridisation_10mminuscyb_24",
                        "NERC_EG_Wertheim_Hybridisation_10mminusdyb_44");
        assertThat(contrast.getReferenceAssayGroup(), is(expectedReferenceAssayGroup));

        AssayGroup expectedTestAssayGroup =
                AssayGroupFactory.create(
                        "g13",
                        "NERC_EG_Wertheim_Hybridisation_10mplusbyb_33",
                        "NERC_EG_Wertheim_Hybridisation_10mpluseyb_74",
                        "NERC_EG_Wertheim_Hybridisation_10mpluscyb_26",
                        "NERC_EG_Wertheim_Hybridisation_10mplusayb_77",
                        "NERC_EG_Wertheim_Hybridisation_10mplusdyb_43");
        assertThat(contrast.getTestAssayGroup(), is(expectedTestAssayGroup));
    }

    @Test
    void shouldHaveDisplayNameEqualsToAccession() {
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);
        assertThat(experiment.getDisplayName(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.getDescription(), startsWith(""));
    }
}
