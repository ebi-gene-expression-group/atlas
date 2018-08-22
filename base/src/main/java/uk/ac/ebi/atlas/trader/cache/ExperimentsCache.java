package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.Experiment;

public abstract class ExperimentsCache<T extends Experiment<? extends DescribesDataColumns>> {

    private final LoadingCache<String, T> experiments;

    public ExperimentsCache(LoadingCache<String, T> experiments) {
        this.experiments = experiments;
    }

    public T getExperiment(String experimentAccession) {
        return experiments.getUnchecked(experimentAccession);
    }

    public void evictExperiment(String experimentAccession) {
        experiments.invalidate(experimentAccession);
    }

}
