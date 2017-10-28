package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentsCacheTest {

    @Mock
    private LoadingCache<String, BaselineExperiment> loadingCacheMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    private RnaSeqBaselineExperimentsCache subject;


    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqBaselineExperimentsCache(loadingCacheMock);
    }

    @Test
    public void testGetExperiment() throws Exception {
        given(loadingCacheMock.get(anyString())).willReturn(baselineExperimentMock);
        assertThat(subject.getExperiment("bla"), is(baselineExperimentMock));
    }

    @Test(expected = ExecutionException.class)
    public void whenGetFromCacheFailsCacheShallThrowExecutionException() throws ExecutionException {
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new FileNotFoundException()));
        subject.getExperiment("");
    }

}