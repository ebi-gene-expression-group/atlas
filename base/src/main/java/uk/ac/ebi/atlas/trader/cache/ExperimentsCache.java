package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.util.concurrent.ExecutionException;

public abstract class ExperimentsCache<T extends Experiment> {

    private final LoadingCache<String, T> experiments;

    public ExperimentsCache(LoadingCache<String, T> experiments){
        this.experiments = experiments;
    }

    public T getExperiment(String experimentAccession) throws ExecutionException {
        return experiments.get(experimentAccession);
    }

    public void evictExperiment(String experimentAccession) {
        experiments.invalidate(experimentAccession);
    }

}
