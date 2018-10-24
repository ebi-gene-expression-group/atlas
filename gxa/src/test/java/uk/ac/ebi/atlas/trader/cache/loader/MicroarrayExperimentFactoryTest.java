package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.Sets;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.arraydesign.ArrayDesignDao;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.arraydesign.ArrayDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentFactoryTest {
    private static final String ACCESSION = "accession";
    private static final String ARRAYDESIGN_ID = "arraydesignId";
    private static final String ARRAYDESIGN_NAME = "arraydesignName";
    private static final String SPECIES_STRING = "species";
    private static final Species SPECIES = new Species(SPECIES_STRING, SpeciesProperties.UNKNOWN);

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private SpeciesFactory speciesFactory;

    @Mock
    private MicroarrayExperimentConfiguration experimentConfigurationMock;

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private IdfParserOutput idfParserOutputMock;

    @Mock
    private ArrayDesignDao arrayDesignDAO;

    private MicroarrayExperimentFactory subject;

    @Before
    public void setUp() {
        subject = new MicroarrayExperimentFactory(configurationTraderMock, speciesFactory, arrayDesignDAO);

        when(experimentDTOMock.getExperimentAccession()).thenReturn(ACCESSION);
        when(experimentDTOMock.getExperimentType()).thenReturn(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
        when(experimentDTOMock.getPubmedIds()).thenReturn(Sets.newHashSet("pubmed1"));
        when(experimentDTOMock.getSpecies()).thenReturn(SPECIES_STRING);

        when(speciesFactory.create(SPECIES_STRING)).thenReturn(SPECIES);

        when(configurationTraderMock.getMicroarrayExperimentConfiguration(ACCESSION))
                .thenReturn(experimentConfigurationMock);
        when(experimentConfigurationMock.getArrayDesignAccessions())
                .thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGN_ID)));
        when(arrayDesignDAO.getArrayDesign(ARRAYDESIGN_ID))
                .thenReturn(ArrayDesign.create(ARRAYDESIGN_ID, ARRAYDESIGN_NAME));
    }

    @Test
    public void testLoad() {
        MicroarrayExperiment microarrayExperiment =
                subject.create(experimentDTOMock, experimentDesignMock, idfParserOutputMock);
        assertThat(microarrayExperiment.getAccession(), is(ACCESSION));
        assertThat(microarrayExperiment.getArrayDesignAccessions(), hasItem(ARRAYDESIGN_ID));
        assertThat(microarrayExperiment.getSpecies(), is(SPECIES));
        assertThat(microarrayExperiment.getExperimentDesign(), is(experimentDesignMock));
        assertThat(microarrayExperiment.getArrayDesignNames(), hasItem(ARRAYDESIGN_NAME));
    }

    @Test
    public void idfTitleOverridesDatabaseTitleInExperimentDescription() {
        when(idfParserOutputMock.getTitle()).thenReturn("IDF title");
        when(experimentDTOMock.getTitle()).thenReturn("DTO title");
        Assert.assertThat(
                subject.create(experimentDTOMock, experimentDesignMock, idfParserOutputMock),
                hasProperty("description", Is.is("IDF title")));
    }

}
