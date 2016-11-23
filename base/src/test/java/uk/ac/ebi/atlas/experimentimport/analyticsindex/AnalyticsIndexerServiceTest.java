package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsIndexerServiceTest {

    @Mock
    SolrClient solrClient;
    @Mock
    ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    private AnalyticsIndexerService subject;

    private Experiment experiment = BaselineExperimentTest.mockExperiment();

    @Before
    public void setUp() throws Exception {
        UpdateResponse r = Mockito.mock(UpdateResponse.class);
        when(r.getQTime()).thenReturn(10);
        when(solrClient.add(Matchers.anyCollectionOf(SolrInputDocument.class))).thenReturn(r);

        subject = new AnalyticsIndexerService(solrClient, experimentDataPointStreamFactory);
    }

    @Test
    public void successfulRunForNoData()throws Exception{
        Mockito.doAnswer(new Answer() {
            @Override
            public Iterable<? extends ExperimentDataPoint> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new ArrayList<>();
            }
        }).when(experimentDataPointStreamFactory).stream(experiment);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 8000;


        int response = subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);

        Mockito.verify(solrClient, times(0)).add(Matchers.anyCollectionOf(SolrInputDocument.class));

        assertThat(response, is(0));
    }

    @Test
    public void successfulRunForSomeData()throws Exception{

        final ExperimentDataPoint experimentDataPoint1 = Mockito.mock(ExperimentDataPoint.class);
        when(experimentDataPoint1.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList
                .<BioentityPropertyName>of());
        final ExperimentDataPoint experimentDataPoint2 = Mockito.mock(ExperimentDataPoint.class);
        when(experimentDataPoint2.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList
                .<BioentityPropertyName>of());

        Mockito.doAnswer(new Answer() {
            @Override
            public Iterable<? extends ExperimentDataPoint> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ImmutableList.of(experimentDataPoint1, experimentDataPoint2);
            }
        }).when(experimentDataPointStreamFactory).stream(experiment);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        int response = subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);

        Mockito.verify(solrClient, times(2)).add(Matchers.anyCollectionOf(SolrInputDocument.class));

        assertThat(response, is(2));
    }

    @Test(expected=RuntimeException.class)
    public void exceptionsFromIteratorArePropagated()throws Exception{
        final ExperimentDataPoint experimentDataPoint = Mockito.mock(ExperimentDataPoint.class);
        when(experimentDataPoint.getRelevantBioentityPropertyNames()).thenThrow(new RuntimeException("Woosh!"));

        Mockito.doAnswer(new Answer() {
            @Override
            public Iterable<? extends ExperimentDataPoint> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ImmutableList.of(experimentDataPoint);
            }
        }).when(experimentDataPointStreamFactory).stream(experiment);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }

    @Test
    public void exceptionsFromSolrAreLoggedButTheCodeProceeds() throws Exception{
        when(solrClient.add(Matchers.anyCollectionOf(SolrInputDocument.class))).thenThrow(new IOException(""));

        final ExperimentDataPoint experimentDataPoint = Mockito.mock(ExperimentDataPoint.class);
        when(experimentDataPoint.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList
                .<BioentityPropertyName>of());

        Mockito.doAnswer(new Answer() {
            @Override
            public Iterable<? extends ExperimentDataPoint> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return ImmutableList.of(experimentDataPoint);
            }
        }).when(experimentDataPointStreamFactory).stream(experiment);
        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        int response = subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);

        assertThat(response, is(0));
    }
}
