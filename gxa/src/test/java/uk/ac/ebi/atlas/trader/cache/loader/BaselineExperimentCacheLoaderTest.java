package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentCacheLoaderTest {

    private String experimentAccession = "E-MOCK-1";

    private ExperimentDTO dto =
            new ExperimentDTO(
                    experimentAccession, ExperimentType.RNASEQ_MRNA_BASELINE, "homo_sapiens",
                    Collections.emptySet(), "mock experiment",new Date(), false, "accessKeyUUID");
    @Mock
    private ConfigurationTrader configurationTrader ;
    @Mock
    private SpeciesFactory speciesFactoryMock;
    @Mock
    private ExperimentConfiguration configuration;
    @Mock
    private BaselineExperimentConfiguration baselineConfiguration;

    @Mock
    private ExperimentDesign experimentDesign;
    @Spy
    private static MockDataFileHub dataFileHub;

    private BaselineExperimentFactory subject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        dataFileHub = new MockDataFileHub();
    }

    @Before
    public void setUp(){
        AssayGroup assayGroup = mock(AssayGroup.class);
        when(assayGroup.getId()).thenReturn("assay group id 1");

        List<AssayGroup> assayGroups = ImmutableList.of(assayGroup);
        dataFileHub.addTemporaryFile(MessageFormat.format("/magetab/{0}/{0}.tsv", experimentAccession),
                ImmutableSet.of("assay group id 1"));

        subject = new RnaSeqBaselineExperimentFactory(configurationTrader, speciesFactoryMock, dataFileHub);
        when(configurationTrader.getExperimentConfiguration(experimentAccession)).thenReturn(configuration);
        when(configurationTrader.getBaselineFactorsConfiguration(experimentAccession)).thenReturn(baselineConfiguration);
        when(baselineConfiguration.getDefaultQueryFactorType()).thenReturn("ORGANISM_PART");
        when(configuration.getAssayGroups()).thenReturn(assayGroups);

        when(speciesFactoryMock.create(dto.getSpecies())).thenReturn(new Species("Homo sapiens",
                        SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of())));

    }

    private void verifyCollaborators() {
        verify(configurationTrader).getExperimentConfiguration(experimentAccession);
        verify(configurationTrader).getBaselineFactorsConfiguration(experimentAccession);
        verify(configuration).getAssayGroups();
        verify(speciesFactoryMock).create(dto.getSpecies());
    }

    private void noMoreInteractionsWithCollaborators() {
        verifyNoMoreInteractions(configurationTrader,
                speciesFactoryMock);
    }

    @Test(expected=IllegalStateException.class)
    public void assayGroupsShouldBeNonEmpty() throws Exception{
        when(configuration.getAssayGroups()).thenReturn(ImmutableList.of());
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
        BaselineExperimentConfiguration alternativeViewBaselineConfiguration =
                mock(BaselineExperimentConfiguration.class);
        when(configurationTrader.getBaselineFactorsConfiguration(alternativeViewAccession))
                .thenReturn(alternativeViewBaselineConfiguration);
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

    
}
