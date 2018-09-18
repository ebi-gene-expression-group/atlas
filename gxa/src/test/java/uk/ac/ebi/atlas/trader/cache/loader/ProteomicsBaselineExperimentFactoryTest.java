package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProteomicsBaselineExperimentFactoryTest {
    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private ExperimentConfiguration configurationMock;

    @Mock
    private BaselineExperimentConfiguration baselineConfigurationMock;

    @Mock
    private SpeciesFactory speciesFactoryMock;

    @Mock
    private ExperimentDTO experimentDtoMock;

    @Mock
    private IdfParserOutput idfParserOutputMock;

    private ProteomicsBaselineExperimentFactory subject;

    @BeforeEach
    void setUp() {
        when(experimentDtoMock.getSpecies()).thenReturn("Crocubot");
        when(speciesFactoryMock.create("Crocubot")).thenReturn(new Species("Crocubot", SpeciesProperties.UNKNOWN));

        when(experimentDtoMock.getLastUpdate()).thenReturn(new Date());
        when(experimentDtoMock.getPubmedIds()).thenReturn(ImmutableSet.of());
        when(experimentDtoMock.getDois()).thenReturn(ImmutableSet.of());

        when(baselineConfigurationMock.disclaimer()).thenReturn("");

        when(configurationMock.getAssayGroups()).thenReturn(ImmutableList.of(AssayGroupFactory.create("Assay X", "X1")));

        when(baselineConfigurationMock.getDefaultFilterFactors()).thenReturn(ImmutableSet.of());
        when(baselineConfigurationMock.getDefaultQueryFactorType()).thenReturn("SOME_FACTOR");
        when(baselineConfigurationMock.getMenuFilterFactorTypes()).thenReturn(ImmutableList.of());
        when(baselineConfigurationMock.getDataProviderURL()).thenReturn(ImmutableList.of());
        when(baselineConfigurationMock.getDataProviderDescription()).thenReturn(ImmutableList.of());

        subject = new ProteomicsBaselineExperimentFactory(configurationTraderMock, speciesFactoryMock);
    }

    @Test
    void idfTitleOverridesDatabaseTitle() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        when(configurationTraderMock.getExperimentConfiguration(experimentAccession))
                .thenReturn(configurationMock);
        when(configurationTraderMock.getBaselineFactorsConfiguration(experimentAccession))
                .thenReturn(baselineConfigurationMock);
        when(experimentDtoMock.getExperimentAccession()).thenReturn(experimentAccession);

        when(idfParserOutputMock.getTitle()).thenReturn("IDF " + experimentAccession);
        when(baselineConfigurationMock.getExperimentDisplayName()).thenReturn("DTO " + experimentAccession);

        assertThat(subject.create(experimentDtoMock, new ExperimentDesign(), idfParserOutputMock))
                .hasFieldOrPropertyWithValue("description", "IDF " + experimentAccession);
    }
}
