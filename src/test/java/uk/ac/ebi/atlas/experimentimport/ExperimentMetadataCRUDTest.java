
package uk.ac.ebi.atlas.experimentimport;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileService;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf.*;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentMetadataCRUDTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXPERIMENT_ASSAY = "EXPERIMENT_ASSAY";
    private static final String ARRAY_DESIGN = "ARRAY_DESIGN";

    private static final String EFO_0000761 = "EFO_0000761";
    private static final String EFO_0000001 = "EFO_0000001";
    private static final String EFO_0001443 = "snap#SpecificallyDependentContinuant";
    private static final String EFO_0001438 = "snap#Disposition";
    private static final Set<String> EXPANDED_EFO_TERMS = new HashSet<>(Arrays.asList(EFO_0001438, EFO_0001443, EFO_0000001));

    private ExperimentMetadataCRUD subject;

    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private ArrayDesignDAO arrayDesignDAOMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    @Mock
    private ExperimentDesignFileService experimentDesignFileServiceMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private ConditionsIndexTrader conditionsIndexTraderMock;

    @Mock
    private ConditionsIndex conditionsIndexMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    @Mock
    private CondensedSdrfParser condensedSdrfParserMock;

    @Mock
    private CondensedSdrfParserOutput condensedSdrfParserOutputMock;

    @Mock
    private EFOLookupService efoParentsLookupServiceMock;

    @Mock
    private AnalyticsIndexerManager analyticsIndexerManagerMock;

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Captor
    private ArgumentCaptor<String> experimentAccessionCaptor;

    private ImmutableSetMultimap<String,String> allOntologyTermIdsByAssayAccession = ImmutableSetMultimap.of
            (EXPERIMENT_ASSAY,EFO_0000761);

    ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    @Before
    public void setUp() throws Exception {

        when(microarrayExperimentConfigurationMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));
        when(experimentConfigurationMock.getExperimentType()).thenReturn(experimentType);

        given(conditionsIndexTraderMock.getIndex(any(ExperimentType.class))).willReturn(conditionsIndexMock);
        given(conditionsIndexTraderMock.getIndex(any(ExperimentType.class))).willReturn(conditionsIndexMock);
        doNothing().when(conditionsIndexMock).removeConditions(anyString());
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);

        when(experimentDAOMock.findExperiment(anyString(), anyBoolean())).thenReturn(experimentDTOMock);

        given(experimentDTOMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(experimentDTOMock.getExperimentType()).willReturn(experimentType);

        given(condensedSdrfParserMock.parse(anyString(), any(ExperimentType.class))).willReturn(condensedSdrfParserOutputMock);
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(experimentDesignMock);

        given(condensedSdrfParserOutputMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(condensedSdrfParserOutputMock.getExperimentType()).willReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        given(condensedSdrfParserOutputMock.getPubmedIds()).willReturn(new ImmutableSet.Builder<String>().build());
        given(condensedSdrfParserOutputMock.getTitle()).willReturn("");

        when(experimentDesignMock.getAllOntologyTermIdsByAssayAccession()).thenReturn(allOntologyTermIdsByAssayAccession);
        when(efoParentsLookupServiceMock.getAllParents(anySetOf(String.class))).thenReturn(EXPANDED_EFO_TERMS);

        subject = new ExperimentMetadataCRUD(experimentDAOMock, experimentTraderMock, condensedSdrfParserMock, efoParentsLookupServiceMock);
        subject.setConditionsIndexTrader(conditionsIndexTraderMock);
        subject.setExperimentDesignFileService(experimentDesignFileServiceMock);
        subject.setAnalyticsIndexerManager(analyticsIndexerManagerMock);
    }

    @Test
    public void generateExperimentDesignShouldUseTheExperimentDesignWriter() throws Exception {

        subject.writeExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
        verify(experimentDesignFileServiceMock).write(EXPERIMENT_ACCESSION,experimentType,experimentDesignMock);
    }

    @Test(expected = IOException.class)
    public void shouldThrowIllegalStateExceptionWhenWritingExperimentDesignFails() throws Exception {
        willThrow(new IOException()).given(experimentDesignFileServiceMock).write(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE,experimentDesignMock);
        subject.writeExperimentDesignFile(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesignMock);
    }

    @Test
    public void updateExperimentToPrivateShouldDelegateToDAO() throws Exception {
        subject.makeExperimentPrivate(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, true);
    }

    @Test
    public void updateExperimentToPublicShouldDelegateToDAO() throws Exception {
        given(experimentDAOMock.findExperiment(EXPERIMENT_ACCESSION, false)).willReturn(experimentDTOMock);
        subject.makeExperimentPublic(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentDesignShouldRemoveExperimentFromCache() throws Exception {
        subject.updateExperimentDesign(ExperimentDTO.createNew(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(experimentTraderMock).removeExperimentFromCache(EXPERIMENT_ACCESSION);
        verify(conditionsIndexMock).updateConditions(any(Experiment.class), Matchers.<SetMultimap<String,String>>any());
    }

    @Test
    public void importExperimentShouldAddExperimentToIndex() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, experimentConfigurationMock, false, Optional.<String>absent());
        verify(conditionsIndexMock).addConditions(any(Experiment.class), Matchers.<SetMultimap<String,String>>any());
    }

    @Test
    public void importExperimentExpandsOntologyTerms() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, experimentConfigurationMock, false, Optional.<String>absent());
        verify(efoParentsLookupServiceMock).expandOntologyTerms(allOntologyTermIdsByAssayAccession);
    }

    @Test
    public void updateExperimentExpandsOntologyTerms() throws Exception {
        subject.updateExperimentDesign(ExperimentDTO.createNew(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(efoParentsLookupServiceMock).expandOntologyTerms(allOntologyTermIdsByAssayAccession);
    }

    @Test
    public void deleteExperimentShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.deleteExperiment(ExperimentDTO.createNew(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(analyticsIndexerManagerMock).deleteFromAnalyticsIndex(experimentAccessionCaptor.capture());

        assertThat(experimentAccessionCaptor.getValue(), is(EXPERIMENT_ACCESSION));
    }

    @Test
    public void updateExperimentToPrivateShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.makeExperimentPrivate(EXPERIMENT_ACCESSION);
        verify(analyticsIndexerManagerMock).deleteFromAnalyticsIndex(experimentAccessionCaptor.capture());

        assertThat(experimentAccessionCaptor.getValue(), is(EXPERIMENT_ACCESSION));
    }

}