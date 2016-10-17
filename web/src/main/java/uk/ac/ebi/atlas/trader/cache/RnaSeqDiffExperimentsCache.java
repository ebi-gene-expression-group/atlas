package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class RnaSeqDiffExperimentsCache implements ExperimentsCache<DifferentialExperiment> {

    private LoadingCache<String, DifferentialExperiment> experiments;

    @Inject
    public RnaSeqDiffExperimentsCache(@Qualifier("differentialExperimentsLoadingCache") LoadingCache<String, DifferentialExperiment> experiments) {
        this.experiments = experiments;
    }

    @Override
    public DifferentialExperiment getExperiment(String experimentAccession) throws ExecutionException {
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
