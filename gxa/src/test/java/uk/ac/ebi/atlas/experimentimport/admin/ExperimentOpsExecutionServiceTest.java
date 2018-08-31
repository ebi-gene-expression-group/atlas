package uk.ac.ebi.atlas.experimentimport.admin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentOpsExecutionServiceTest {
    private static final String ACCESSION = "E-EXAMPLE-1";

    @Mock
    private ExperimentCrud experimentCrudMock;

    @Mock
    private BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;

    @Mock
    private AnalyticsIndexerManager analyticsIndexerManager;

    @Mock
    private ExperimentTrader experimentTrader;

    @Mock
    private ExperimentAttributesService experimentAttributesService;

    @Mock
    private ExperimentDTO experimentDTO;

    private ExperimentOpsExecutionService subject;

    @Before
    public void setUp() {
        when(experimentCrudMock.findExperiment(ACCESSION)).thenReturn(experimentDTO);
        subject =
                new ExpressionAtlasExperimentOpsExecutionService(
                        experimentCrudMock,
                        baselineCoexpressionProfileLoader,
                        analyticsIndexerManager,
                        experimentTrader,
                        experimentAttributesService);
    }

    @Test
    public void updateExperimentDesignShouldRemoveExperimentFromCache() throws Exception {
        subject.attemptExecuteStatefulOp(ACCESSION, Op.UPDATE_DESIGN);
        verify(experimentTrader).removeExperimentFromCache(ACCESSION);
    }

    @Test
    public void updateExperimentToPrivateShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.attemptExecuteStatefulOp(ACCESSION, Op.UPDATE_PRIVATE);
        verify(analyticsIndexerManager).deleteFromAnalyticsIndex(ACCESSION);
    }

    @Test
    public void deleteExperimentShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.attemptExecuteStatefulOp(ACCESSION, Op.DELETE);
        verify(analyticsIndexerManager).deleteFromAnalyticsIndex(ACCESSION);
    }
}
