
package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
@Scope("singleton")
public class BaselineExperimentsCache implements ExperimentsCache<BaselineExperiment> {

    private LoadingCache<String, BaselineExperiment> experiments;

    @Inject
    public BaselineExperimentsCache(@Qualifier("baselineExperimentsLoadingCache") LoadingCache<String, BaselineExperiment> experiments) {
        this.experiments = experiments;
    }

    @Override
    public BaselineExperiment getExperiment(String experimentAccession) throws ExecutionException {
        return experiments.get(experimentAccession);
    }

    @Override
    public void evictExperiment(String experimentAccession) {
        experiments.invalidate(experimentAccession);
    }

    @Override
    public void evictAll() {
        experiments.invalidateAll();
    }


}
