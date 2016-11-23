package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentFactoryTest {

    private static final String ACCESSION = "accession";
    private static final String ARRAYDESIGN_ID = "arraydesignId";
    private static final String ARRAYDESIGN_NAME = "arraydesignName";
    private static final String SPECIES_STRING = "species";
    private static final Species SPECIES = new Species(SPECIES_STRING, SPECIES_STRING, "ensembl","kingdom");
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private SpeciesKingdomTrader speciesKingdomTraderMock;

    @Mock
    private SpeciesFactory speciesFactory;

    @Mock
    private MicroarrayExperimentConfiguration experimentConfigurationMock;

    @Mock
    ExperimentDTO experimentDTOMock;

    @Mock
    private Contrast contrastMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ArrayDesignTrader arrayDesignTraderMock;

    private MicroarrayExperimentFactory subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentFactory(configurationTraderMock, speciesFactory, arrayDesignTraderMock);

        when(experimentDTOMock.getExperimentAccession()).thenReturn(ACCESSION);
        when(experimentDTOMock.getExperimentType()).thenReturn(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
        when(experimentDTOMock.getAccessKey()).thenReturn(ACCESS_KEY);
        when(experimentDTOMock.getPubmedIds()).thenReturn(Sets.newHashSet("pubmed1"));

        when(speciesFactory.create(experimentDTOMock)).thenReturn(SPECIES);

        when(configurationTraderMock.getMicroarrayExperimentConfiguration(ACCESSION)).thenReturn(experimentConfigurationMock);
        when(experimentConfigurationMock.getContrasts()).thenReturn(Sets.newHashSet(contrastMock));
        when(experimentConfigurationMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGN_ID)));
        when(arrayDesignTraderMock.getArrayDesignNames(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGN_ID)))).thenReturn
                (Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGN_NAME)));

        when(experimentDAOMock.findPublicExperiment(ACCESSION)).thenReturn(experimentDTOMock);
    }

    @Test
    public void testLoad() throws Exception {
        MicroarrayExperiment microarrayExperiment = subject.create(experimentDTOMock, "description",
                experimentDesignMock);
        assertThat(microarrayExperiment.getAccession(), is(ACCESSION));
        assertThat(microarrayExperiment.getArrayDesignAccessions(), hasItem(ARRAYDESIGN_ID));
        assertThat(microarrayExperiment.getSpecies(), is(SPECIES));
        assertThat(microarrayExperiment.getExperimentDesign(), is(experimentDesignMock));
        assertThat(microarrayExperiment.getArrayDesignNames(), hasItem(ARRAYDESIGN_NAME));
    }
}