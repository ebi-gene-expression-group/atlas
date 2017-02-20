package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.util.concurrent.ExecutionException;

public abstract class ExperimentsCache<T extends Experiment> {

    private final LoadingCache<String, T> experiments;

    public ExperimentsCache(LoadingCache<String, T> experiments){
        this.experiments = experiments;
    }

    public T getExperiment(String experimentAccession) {
        try {
            return experiments.get(experimentAccession);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to create experiment from cache: " + experimentAccession);
        }
    }

    public void evictExperiment(String experimentAccession) {
        experiments.invalidate(experimentAccession);
    }

    public void evictAll() {
        experiments.invalidateAll();
    }

}
