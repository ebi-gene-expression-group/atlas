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

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsCacheTest {

    @Mock
    private LoadingCache<String, BaselineExperiment> loadingCacheMock;

    private RnaSeqBaselineExperimentsCache subject;

    @Before
    public void init() throws Exception {
        subject = new RnaSeqBaselineExperimentsCache(loadingCacheMock);
    }

    @Test(expected = ExecutionException.class)
    public void whenGetFromCacheFailsCacheShallThrowExecutionException() throws ExecutionException {
        given(loadingCacheMock.get("")).willThrow(new ExecutionException(new FileNotFoundException()));
        subject.getExperiment("");
    }

}
