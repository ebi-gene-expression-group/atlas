package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsCacheTest {

    @Mock
    private LoadingCache<String, List<ExperimentRun>> loadingCacheMock;

    private ExperimentsCache subject;

    @Before
    public void init() throws Exception {

        subject = new ExperimentsCache(loadingCacheMock);
    }


    @Test(expected = IllegalStateException.class)
    public void whenGetFromCacheFailsCacheShallThrowIllegalStateException() throws ExecutionException {
        //given
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new MalformedURLException()));
        //when
        subject.getExperimentRuns("");
        //then should throw IllegalStateException

    }
}
