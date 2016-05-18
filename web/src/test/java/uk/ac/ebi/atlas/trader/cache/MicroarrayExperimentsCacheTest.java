
package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentsCacheTest {

    @Mock
    private LoadingCache<String, MicroarrayExperiment> loadingCacheMock;

    @Mock
    private MicroarrayExperiment microarrayExperimentMock;

    private MicroarrayExperimentsCache subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentsCache(loadingCacheMock);
    }

    @Test
    public void testGetExperiment() throws Exception {
        given(loadingCacheMock.get(anyString())).willReturn(microarrayExperimentMock);
        assertThat(subject.getExperiment("bla"), is(microarrayExperimentMock));
    }

    @Test(expected = ExecutionException.class)
    public void whenGetFromCacheFailsCacheShallThrowExecutionException() throws ExecutionException {
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new MalformedURLException()));
        subject.getExperiment("");
    }
}