package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPoint;
import uk.ac.ebi.atlas.model.analyticsindex.BaselineExperimentDataPointStream;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsIndexerServiceTest {

    @Mock
    private SolrClient solrClient;
    @Mock
    private ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    private AnalyticsIndexerService subject;

    @Mock
    private BaselineExperiment experiment;

    @Before
    public void setUp() throws Exception {
        UpdateResponse r = Mockito.mock(UpdateResponse.class);
        when(r.getQTime()).thenReturn(10);
        when(solrClient.add(anyCollection())).thenReturn(r);

        subject = new AnalyticsIndexerService(solrClient, experimentDataPointStreamFactory);
    }

    @Test
    public void successfulRunForNoData()throws Exception{
        BaselineExperimentDataPoint[] nullArray = {null};
        mockExperimentDataPointStreamFactory(experimentDataPointStreamFactory, experiment, nullArray);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 8000;

        int response = subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);

        Mockito.verify(solrClient, times(0)).add(anyCollection());

        assertThat(response, is(0));
    }

    @Test
    public void successfulRunForSomeData()throws Exception{

        final BaselineExperimentDataPoint experimentDataPoint1 = Mockito.mock(BaselineExperimentDataPoint.class);
        when(experimentDataPoint1.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList.of());
        final BaselineExperimentDataPoint experimentDataPoint2 = Mockito.mock(BaselineExperimentDataPoint.class);
        when(experimentDataPoint2.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList.of());

        mockExperimentDataPointStreamFactory(
                experimentDataPointStreamFactory, experiment, experimentDataPoint1, experimentDataPoint2, null);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        int response = subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);

        Mockito.verify(solrClient, times(2)).add(anyCollection());

        assertThat(response, is(2));
    }

    @Test(expected=RuntimeException.class)
    public void exceptionsFromIteratorArePropagated()throws Exception{
        final ExperimentDataPoint experimentDataPoint = Mockito.mock(ExperimentDataPoint.class);

        Mockito.doAnswer(invocationOnMock -> ImmutableList.of(experimentDataPoint))
                .when(experimentDataPointStreamFactory).stream(experiment);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }

    @Test(expected=RuntimeException.class)
    public void exceptionsFromSolrAreWrappedAndThrown() throws Exception{
        when(solrClient.add(anyCollection())).thenThrow(new IOException(""));

        final BaselineExperimentDataPoint baselineExperimentDataPointMock =
                Mockito.mock(BaselineExperimentDataPoint.class);
        when(baselineExperimentDataPointMock.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList.of());

        mockExperimentDataPointStreamFactory(
                experimentDataPointStreamFactory, experiment, baselineExperimentDataPointMock, null);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }

    @Test(expected=RuntimeException.class)
    public void exceptionsFromFilesAreWrappedAndThrown() throws Exception{
        when(experimentDataPointStreamFactory.stream(experiment))
                .thenThrow(new NoSuchFileException(String.format("%s-tpms.tsv not found", experiment.getAccession())));

        // The mock above is redundant for the test to throw an exception, uncomment the following to make it fail
        // final BaselineExperimentDataPoint experimentDataPoint1 = Mockito.mock(BaselineExperimentDataPoint.class);
        // when(experimentDataPoint1.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList.of());
        // final BaselineExperimentDataPoint experimentDataPoint2 = Mockito.mock(BaselineExperimentDataPoint.class);
        // when(experimentDataPoint2.getRelevantBioentityPropertyNames()).thenReturn(ImmutableList.of());
        // mockExperimentDataPointStreamFactory(
        //        experimentDataPointStreamFactory, experiment, experimentDataPoint1, experimentDataPoint2, null);

        Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch = new HashMap<>();
        int batchSize = 1;

        subject.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }

    private void mockExperimentDataPointStreamFactory(ExperimentDataPointStreamFactory experimentDataPointStreamFactory,
                                                      BaselineExperiment experiment,
                                                      BaselineExperimentDataPoint... experimentDataPoints)
    throws Exception {
        final ObjectInputStream<BaselineExperimentDataPoint> baselineExperimentDataPointStreamMock =
                Mockito.mock(BaselineExperimentDataPointStream.class);

        when(baselineExperimentDataPointStreamMock.readNext())
                .thenReturn(experimentDataPoints[0],
                        Arrays.copyOfRange(experimentDataPoints, 1, experimentDataPoints.length));

        Mockito.doAnswer(invocationOnMock -> baselineExperimentDataPointStreamMock)
                .when(experimentDataPointStreamFactory).stream(experiment);
    }
}
