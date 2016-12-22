package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactorsFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentCacheLoaderTest {

    class Loader extends BaselineExperimentFactory {

        protected Loader(ExperimentalFactorsFactory experimentalFactorsFactory, ExperimentType experimentType,
                         ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
            super( experimentalFactorsFactory, experimentType, configurationTrader, speciesFactory, dataFileHub);
        }
    }

    private String experimentAccession = "E-MOCK-1";

    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    private ExperimentDTO dto = new ExperimentDTO(experimentAccession, experimentType, "homo_sapiens", Collections
            .<String>emptySet(), "mock experiment",new Date(), false, "accessKeyUUID");
    @Mock
    private ExperimentalFactorsFactory experimentalFactorsFactory;
    @Mock
    private ConfigurationTrader configurationTrader ;
    @Mock
    private SpeciesFactory speciesFactoryMock;
    @Mock
    private ExperimentConfiguration configuration;
    @Mock
    private BaselineExperimentConfiguration baselineConfiguration;
    @Mock
    private ExperimentalFactors experimentalFactors;
    @Mock
    private AssayGroups assayGroups;
    @Mock
    private ExperimentDesign experimentDesign;
    @Spy
    private static MockDataFileHub dataFileHub;

    private BaselineExperimentFactory subject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = MockDataFileHub.get();
    }

    @Before
    public void setUp(){
        Set<String> assayGroupIds =  ImmutableSet.of("assay group id 1");
        dataFileHub.addTemporaryFile(MessageFormat.format("/magetab/{0}/{0}.tsv", experimentAccession),assayGroupIds);

        subject = new Loader(experimentalFactorsFactory,experimentType, configurationTrader, speciesFactoryMock, dataFileHub);
        when(configurationTrader.getExperimentConfiguration(experimentAccession)).thenReturn(configuration);
        when(configurationTrader.getBaselineFactorsConfiguration(experimentAccession)).thenReturn(baselineConfiguration);
        when(configuration.getAssayGroups()).thenReturn(assayGroups);
        when(assayGroups.getAssayGroupIds()).thenReturn(assayGroupIds);
        when(speciesFactoryMock.create(dto.getSpecies())).thenReturn(new Species("Homo sapiens",
                        SpeciesProperties.create("homo sapiens", "Homo_sapiens", "ORGANISM_PART", "animals",
                                ImmutableSortedMap.<String, List<String>>of())));

                when(experimentalFactorsFactory.createExperimentalFactors(eq(experimentAccession),eq(experimentDesign),
                eq(baselineConfiguration), eq(assayGroups), any(String [] .class), anyBoolean())).thenReturn
                (experimentalFactors);
    }

    private void verifyCollaborators() {
        verify(configurationTrader).getExperimentConfiguration(experimentAccession);
        verify(configurationTrader).getBaselineFactorsConfiguration(experimentAccession);
        verify(configuration).getAssayGroups();
        verify(experimentalFactorsFactory).createExperimentalFactors(eq(experimentAccession),eq(experimentDesign),
                eq(baselineConfiguration), eq(assayGroups), any(String [] .class), anyBoolean());
        verify(speciesFactoryMock).create(dto.getSpecies());
        if(!baselineConfiguration.orderCurated()){
            verify(dataFileHub, atLeastOnce()).getBaselineExperimentFiles(experimentAccession);
        }
    }

    private void noMoreInteractionsWithCollaborators() {
        verifyNoMoreInteractions(experimentalFactorsFactory, configurationTrader,
                speciesFactoryMock);
    }

    @Test(expected=IllegalStateException.class)
    public void assayGroupsShouldBeNonEmpty() throws Exception{
        when(configuration.getAssayGroups()).thenReturn(mock(AssayGroups.class));
        subject.create(dto, "description from array express", experimentDesign);
    }

    @Test
    public void useAllCollaborators() throws Exception {
        subject.create(dto, "description from array express", experimentDesign);
        verifyCollaborators();
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void noAlternativeViewsForTypicalExperiment() throws Exception {
        BaselineExperiment e = subject.create(dto, "description from array express", experimentDesign);

        assertThat(e.alternativeViews(), hasSize(0));
        verifyCollaborators();
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void alternativeViews() throws Exception {
        String alternativeViewAccession = "E-MOCK-2";
        when(baselineConfiguration.getAlternativeViews()).thenReturn(ImmutableList.of(alternativeViewAccession));
        BaselineExperimentConfiguration alternativeViewBaselineConfiguration = mock(BaselineExperimentConfiguration
                .class);
        when(configurationTrader.getBaselineFactorsConfiguration(alternativeViewAccession)).thenReturn
                (alternativeViewBaselineConfiguration);
        String s = "default query factor of other experiment";
        when(alternativeViewBaselineConfiguration.getDefaultQueryFactorType()).thenReturn(s);

        BaselineExperiment e = subject.create(dto, "description from array express", experimentDesign);

        assertThat(e.alternativeViews(), hasSize(1));
        assertThat(e.alternativeViews().get(0).getLeft(), is(alternativeViewAccession));
        assertThat(e.alternativeViews().get(0).getRight(), containsString(s));
        verifyCollaborators();
        verify(baselineConfiguration, atLeastOnce()).getAlternativeViews();
        verify(alternativeViewBaselineConfiguration).getDefaultQueryFactorType();
        verify(configurationTrader).getBaselineFactorsConfiguration(alternativeViewAccession);
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void extractAssayGroupIdsProteomics() {
        String[] tsvFileHeader = "GeneID\tg1.SpectralCount\tg2.SpectralCount\tg3.SpectralCount\tg4.SpectralCount\tg5.SpectralCount\tg6.SpectralCount\tg7.SpectralCount\tg8.SpectralCount\tg9.SpectralCount\tg10.SpectralCount\tg11.SpectralCount\tg12.SpectralCount\tg13.SpectralCount\tg14.SpectralCount\tg15.SpectralCount\tg16.SpectralCount\tg17.SpectralCount\tg18.SpectralCount\tg19.SpectralCount\tg20.SpectralCount\tg21.SpectralCount\tg22.SpectralCount\tg23.SpectralCount\tg24.SpectralCount\tg25.SpectralCount\tg26.SpectralCount\tg27.SpectralCount\tg28.SpectralCount\tg29.SpectralCount\tg30.SpectralCount\tg1.WithInSampleAbundance\tg2.WithInSampleAbundance\tg3.WithInSampleAbundance\tg4.WithInSampleAbundance\tg5.WithInSampleAbundance\tg6.WithInSampleAbundance\tg7.WithInSampleAbundance\tg8.WithInSampleAbundance\tg9.WithInSampleAbundance\tg10.WithInSampleAbundance\tg11.WithInSampleAbundance\tg12.WithInSampleAbundance\tg13.WithInSampleAbundance\tg14.WithInSampleAbundance\tg15.WithInSampleAbundance\tg16.WithInSampleAbundance\tg17.WithInSampleAbundance\tg18.WithInSampleAbundance\tg19.WithInSampleAbundance\tg20.WithInSampleAbundance\tg21.WithInSampleAbundance\tg22.WithInSampleAbundance\tg23.WithInSampleAbundance\tg24.WithInSampleAbundance\tg25.WithInSampleAbundance\tg26.WithInSampleAbundance\tg27.WithInSampleAbundance\tg28.WithInSampleAbundance\tg29.WithInSampleAbundance\tg30.WithInSampleAbundance".split("\t");
        assertThat(subject.readOrderedAssayGroupIds(tsvFileHeader, ExperimentType.PROTEOMICS_BASELINE), is(new String[]{"g1", "g2",
                "g3", "g4", "g5",
                "g6", "g7", "g8", "g9",
                "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21", "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"}));
    }
    
}
