package uk.ac.ebi.atlas.experimentimport.admin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentOpsExecutionServiceTest {


    @Mock
    ExperimentCrud experimentCrud;
    @Mock
    ExperimentOpLogWriter experimentOpLogWriter;
    @Mock
    BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    @Mock
    AnalyticsIndexerManager analyticsIndexerManager;
    @Mock
    ExpressionSerializerService expressionSerializerService;
    @Mock
    ExperimentTrader experimentTrader;

    String accession = "E-EXAMPLE-1";

    ExperimentOpsExecutionService subject;

    @Before
    public void setUp(){
        subject = new ExpressionAtlasExperimentOpsExecutionService(experimentCrud,baselineCoexpressionProfileLoader,analyticsIndexerManager,
                expressionSerializerService, experimentTrader );
    }

    @Test
    public void updateExperimentDesignShouldRemoveExperimentFromCache() throws Exception{
        subject.attemptExecuteStatefulOp(accession, Op.UPDATE_DESIGN_ONLY);
        verify(experimentTrader).removeExperimentFromCache(accession);
    }

    @Test
    public void updateExperimentToPrivateShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.attemptExecuteStatefulOp(accession, Op.UPDATE_PRIVATE);
        verify(analyticsIndexerManager).deleteFromAnalyticsIndex(accession);
    }

    @Test
    public void deleteExperimentShouldRemoveExperimentFromAnalyticsIndex() throws Exception {
        subject.attemptExecuteStatefulOp(accession, Op.DELETE);
        verify(analyticsIndexerManager).deleteFromAnalyticsIndex(accession);
    }


}