
package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqDiffExperimentsCacheTest {

    @Mock
    private LoadingCache<String, DifferentialExperiment> loadingCacheMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    private RnaSeqDiffExperimentsCache subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqDiffExperimentsCache(loadingCacheMock);
    }

    @Test
    public void testGetExperiment() throws Exception {
        given(loadingCacheMock.get(anyString())).willReturn(differentialExperimentMock);
        assertThat(subject.getExperiment("bla"), is(differentialExperimentMock));
    }

    @Test(expected = ExecutionException.class)
    public void whenGetFromCacheFailsCacheShallThrowExecutionException() throws ExecutionException {
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new FileNotFoundException()));
        subject.getExperiment("");
    }
}