package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.PublicExperimentTypesCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionAtlasExperimentTraderTest {

    private static final String E_TABM_713 = "E-TABM-713";
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_599 = "E-MTAB-599";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_GEOD_21860 = "E-GEOD-21860";
    private static final String E_PROT_1 = "E-PROT-1";

    private ExpressionAtlasExperimentTrader subject;

    @Mock
    private ExperimentDAO experimentDAOMock;
    @Mock
    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCacheMock;
    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;
    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;
    @Mock
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCacheMock;
    @Mock
    private PublicExperimentTypesCache publicExperimentTypesCacheMock;

    @Mock
    ExperimentDTO experimentDTOMock;



    @Before
    public void initSubject(){

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE)).thenReturn(Sets.newHashSet(E_MTAB_513, E_MTAB_599));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_GEOD_22351, E_GEOD_38400, E_GEOD_21860));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_MTAB_1066));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_GEOD_43049));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)).thenReturn(Sets.newHashSet(E_TABM_713));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE)).thenReturn(Sets.newHashSet(E_PROT_1));

        when(experimentDAOMock.findPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE,ExperimentType.PROTEOMICS_BASELINE)).thenReturn(Sets
                .newHashSet(E_MTAB_513, E_MTAB_599, E_PROT_1));

        subject = new ExpressionAtlasExperimentTrader(experimentDAOMock,
                rnaSeqBaselineExperimentsCacheMock,
                                        rnaSeqDiffExperimentsCacheMock,
                                        microarrayExperimentsCacheMock,
                                        proteomicsBaselineExperimentsCacheMock,
                publicExperimentTypesCacheMock);
    }

    @Test
    public void experimentOfAnyTypeWillGoIntoSomeCache(){
        for(ExperimentType t: subject.experimentCachesPerType.keySet()){
            assertThat(subject.experimentCachesPerType.get(t), notNullValue());
        }
    }

    @Test
    public void testGetBaselineExperimentsIdentifiers() throws Exception {
        assertThat(subject.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE), containsInAnyOrder(E_MTAB_513, E_MTAB_599));
    }

    @Test
    public void testGetDifferentialExperimentsIdentifiers() throws Exception {
        assertThat(subject.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL), containsInAnyOrder(E_GEOD_22351, E_GEOD_38400,
                E_GEOD_21860));
    }

    @Test
    public void testGetMicroarrayExperimentsIdentifiers() throws Exception {
        assertThat(subject.getMicroarrayExperimentAccessions(), containsInAnyOrder(E_MTAB_1066, E_GEOD_43049, E_TABM_713));
    }

    @Test
    public void getExperimentShouldUseTheCache() throws ExecutionException {
        given(publicExperimentTypesCacheMock.getExperimentType(E_GEOD_21860)).willReturn(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
        subject.getPublicExperiment(E_GEOD_21860);
        verify(rnaSeqBaselineExperimentsCacheMock,times(0)).getExperiment(E_GEOD_21860);
        verify(rnaSeqDiffExperimentsCacheMock, times(0)).getExperiment(E_GEOD_21860);
        verify(microarrayExperimentsCacheMock).getExperiment(E_GEOD_21860);
    }

    @Test
    public void getAllBaselineExperiments() {
        assertThat(subject.getAllBaselineExperimentAccessions(), containsInAnyOrder(E_MTAB_513, E_MTAB_599, E_PROT_1));
    }

}
