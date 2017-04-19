package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class DifferentialExperimentsCacheLoaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-22351";
    private String species = "Mus musculus";

    @Inject
    private DifferentialExperimentFactory differentialExperimentFactory;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private ArrayExpressClient arrayExpressClient;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    private ExperimentsCacheLoader<DifferentialExperiment> subject;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Set<String> pubMedIds = Collections.emptySet();
        ExperimentDTO experimentDTO = new ExperimentDTO(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                species, pubMedIds, "title", new Date(),
                false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(EXPERIMENT_ACCESSION, true)).thenReturn(experimentDTO);

        subject = new ExperimentsCacheLoader<>(arrayExpressClient,experimentDesignParser,experimentDao,
                differentialExperimentFactory );
    }

    @Test
    public void shouldHaveExactlyOneSpecies() throws Exception {

        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getSpecies().getName(), is(species));
    }

    @Test
    public void shouldContainOneContrast() throws Exception {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getDataColumnDescriptors().size(), is(1));
        assertThat(experiment.getDataColumnDescriptors().iterator().next().getDisplayName(), startsWith("'expressing"));
    }

    @Test
    public void shouldContainGivenContrast() throws Exception {
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //when
        Contrast contrast = experiment.getDataColumnDescriptor("g1_g2");

        //then
        assertThat(contrast.getId(), is("g1_g2"));
        assertThat(contrast.getDisplayName(), startsWith("'expressing"));

        AssayGroup expectedAssayGroup = new AssayGroup("g1", "SRR057596", "SRR057597", "SRR057598");
        assertThat(contrast.getReferenceAssayGroup(), is(expectedAssayGroup));

        expectedAssayGroup = new AssayGroup("g2", "SRR057599", "SRR057600", "SRR057601");
        assertThat(contrast.getTestAssayGroup(), is(expectedAssayGroup));
    }

    @Test
    public void shouldHaveDisplayNameEqualsToAccession() throws Exception {
        when(arrayExpressClient.fetchExperimentName(EXPERIMENT_ACCESSION)).thenThrow(new RuntimeException("Woosh!"));
        //given
        DifferentialExperiment experiment = subject.load(EXPERIMENT_ACCESSION);

        //then
        assertThat(experiment.getDisplayName(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.getDescription(), startsWith(""));
    }

}
