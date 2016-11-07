package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactorsFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import java.util.Collections;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentCacheLoaderTest {

    class Loader extends BaselineExperimentsCacheLoader {

        protected Loader(ExperimentalFactorsFactory experimentalFactorsFactory, ExperimentType experimentType,
                         BaselineExperimentExpressionLevelFile expressionLevelFile,
                         ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory) {
            super(experimentalFactorsFactory, experimentType, expressionLevelFile, configurationTrader,speciesFactory);
        }
    }

    String experimentAccession = "E-MOCK-1";

    ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    ExperimentDTO dto = new ExperimentDTO(experimentAccession, experimentType, "homo_sapiens", Collections
            .<String>emptySet(), "mock experiment",new Date(), false, "accessKeyUUID");
    @Mock
    ExperimentalFactorsFactory experimentalFactorsFactory;
    @Mock
    BaselineExperimentExpressionLevelFile expressionLevelFile;
    @Mock
    ConfigurationTrader configurationTrader ;
    @Mock
    SpeciesFactory speciesFactory;
    @Mock
    ExperimentConfiguration configuration;
    @Mock
    BaselineExperimentConfiguration baselineConfiguration;
    @Mock
    ExperimentalFactors experimentalFactors;
    @Mock
    AssayGroups assayGroups;
    @Mock
    ExperimentDesign experimentDesign;

    BaselineExperimentsCacheLoader subject;

    @Before
    public void setUp(){
        subject = new Loader(experimentalFactorsFactory,experimentType,expressionLevelFile,configurationTrader,
                speciesFactory);
        when(configurationTrader.getExperimentConfiguration(experimentAccession)).thenReturn(configuration);
        when(configurationTrader.getBaselineFactorsConfiguration(experimentAccession)).thenReturn(baselineConfiguration);
        when(configuration.getAssayGroups()).thenReturn(assayGroups);
        when(assayGroups.getAssayGroupIds()).thenReturn(ImmutableSet.of("assay group id 1"));
        when(speciesFactory.create(dto,baselineConfiguration)).thenReturn(new Species("homo_sapiens","homo_sapiens","kingdom",
                "ensembl_db"));

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
        verify(speciesFactory).create(dto,baselineConfiguration);
        if(!baselineConfiguration.orderCurated()){
            verify(expressionLevelFile).readOrderedAssayGroupIds(experimentAccession);
        }
    }

    private void noMoreInteractionsWithCollaborators() {
        verifyNoMoreInteractions(experimentalFactorsFactory, expressionLevelFile, configurationTrader,
                speciesFactory);
    }

    @Test(expected=IllegalStateException.class)
    public void assayGroupsShouldBeNonEmpty() throws Exception{
        when(configuration.getAssayGroups()).thenReturn(Mockito.mock(AssayGroups.class));
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);
    }

    @Test
    public void useAllCollaborators() throws Exception {
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);
        verifyCollaborators();
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void noAlternativeViewsForTypicalExperiment() throws Exception {
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);

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

        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);

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
