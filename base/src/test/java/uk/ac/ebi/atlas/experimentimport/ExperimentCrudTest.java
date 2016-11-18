package uk.ac.ebi.atlas.experimentimport;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndex;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexingService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCrudTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXPERIMENT_ASSAY = "EXPERIMENT_ASSAY";

    private static final String EFO_0000761 = "EFO_0000761";

    private ExperimentCrud subject;

    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriterMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private ArrayDesignDAO arrayDesignDAOMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

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
    private AnalyticsIndexerManager analyticsIndexerManagerMock;

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Mock
    ExperimentDesignFileWriterService experimentDesignFileWriterService;

    @Mock
    ConditionsIndexingService conditionsIndexingService;

    @Mock
    ExperimentChecker experimentChecker;

    @Mock
    AnalyticsLoaderFactory analyticsLoaderFactory;

    @Mock
    AnalyticsLoader analyticsLoader;

    @Mock
    ConfigurationTrader configurationTrader;

    @Captor
    private ArgumentCaptor<String> experimentAccessionCaptor;

    private ImmutableSetMultimap<String,String> allOntologyTermIdsByAssayAccession = ImmutableSetMultimap.of
            (EXPERIMENT_ASSAY,EFO_0000761);

    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    private String accessKey = "accessKey";

    @Before
    public void setUp() throws Exception {
        when(analyticsLoaderFactory.getLoader(experimentType)).thenReturn(analyticsLoader);

        when(configurationTrader.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn
                (experimentConfigurationMock);

        when(experimentConfigurationMock.getExperimentType()).thenReturn(experimentType);

        doNothing().when(conditionsIndexMock).removeConditions(anyString());
        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);

        when(experimentDAOMock.findExperiment(anyString(), anyBoolean())).thenReturn(experimentDTOMock);

        given(experimentDTOMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(experimentDTOMock.getExperimentType()).willReturn(experimentType);
        given(experimentDTOMock.getAccessKey()).willReturn(accessKey);

        given(condensedSdrfParserMock.parse(anyString(), any(ExperimentType.class))).willReturn(condensedSdrfParserOutputMock);
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(experimentDesignMock);

        given(condensedSdrfParserOutputMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(condensedSdrfParserOutputMock.getExperimentType()).willReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        given(condensedSdrfParserOutputMock.getPubmedIds()).willReturn(new ImmutableSet.Builder<String>().build());
        given(condensedSdrfParserOutputMock.getTitle()).willReturn("");

        when(experimentDesignMock.getAllOntologyTermIdsByAssayAccession()).thenReturn(allOntologyTermIdsByAssayAccession);

        subject = new ExperimentCrud(condensedSdrfParserMock,
                experimentDesignFileWriterService,
                conditionsIndexingService,experimentDAOMock, analyticsIndexerManagerMock,experimentTraderMock,
                experimentChecker,
                analyticsLoaderFactory,configurationTrader );
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
        verify(conditionsIndexingService).indexConditions(EXPERIMENT_ACCESSION, experimentType, experimentDesignMock);
    }

    @Test
    public void importExperimentIndexesConditions() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
        verify(conditionsIndexingService).indexConditions(EXPERIMENT_ACCESSION, experimentType, experimentDesignMock);
    }

    @Test
    public void updateExperimentIndexesConditions() throws Exception {
        subject.updateExperimentDesign(ExperimentDTO.createNew(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE, null, null, null, false));
        verify(conditionsIndexingService).indexConditions(EXPERIMENT_ACCESSION, experimentType, experimentDesignMock);
    }

    @Test
    public void deleteExperimentShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.deleteExperiment(EXPERIMENT_ACCESSION);
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