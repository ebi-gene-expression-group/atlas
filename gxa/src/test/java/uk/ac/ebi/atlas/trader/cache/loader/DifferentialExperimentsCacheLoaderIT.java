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
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class DifferentialExperimentsCacheLoaderIT {
    private static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";
    private String species = "Mus musculus";

    @Inject
    private DifferentialExperimentFactory differentialExperimentFactory;

    @Mock
    private GxaExperimentDao expressionAtlasExperimentDao;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    @Inject
    private IdfParser idfParser;

    private ExperimentsCacheLoader<DifferentialExperiment> subject;

    @Before
    public void setUp() {
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
    public void shouldHaveExactlyOneSpecies() {

        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getSpecies().getName(), is(species));
    }

    @Test
    public void shouldContainOneContrast() {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getDataColumnDescriptors().size(), is(1));
        assertThat(experiment.getDataColumnDescriptors().iterator().next().getDisplayName(), startsWith("'expressing"));
    }

    @Test
    public void shouldContainGivenContrast() {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //when
        Contrast contrast = experiment.getDataColumnDescriptor("g1_g2");

        //then
        assertThat(contrast.getId(), is("g1_g2"));
        assertThat(contrast.getDisplayName(), startsWith("'expressing"));

        AssayGroup expectedAssayGroup = AssayGroupFactory.create("g1", "SRR057596", "SRR057597", "SRR057598");
        assertThat(contrast.getReferenceAssayGroup(), is(expectedAssayGroup));

        expectedAssayGroup = AssayGroupFactory.create("g2", "SRR057599", "SRR057600", "SRR057601");
        assertThat(contrast.getTestAssayGroup(), is(expectedAssayGroup));
    }

    @Test
    public void shouldHaveDisplayNameEqualsToAccession() {
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);
        assertThat(experiment.getDisplayName(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.getDescription(), startsWith(""));
    }
}
