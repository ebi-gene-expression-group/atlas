package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class MicroarrayExperimentsCache implements ExperimentsCache<MicroarrayExperiment> {

    private LoadingCache<String, MicroarrayExperiment> experiments;

    @Inject
    public MicroarrayExperimentsCache(@Qualifier("microarrayExperimentsLoadingCache") LoadingCache<String, MicroarrayExperiment> experiments) {
        this.experiments = experiments;
    }

    @Override
    public MicroarrayExperiment getExperiment(String experimentAccession) throws ExecutionException {
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
