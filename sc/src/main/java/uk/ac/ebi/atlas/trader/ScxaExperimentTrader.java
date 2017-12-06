package uk.ac.ebi.atlas.trader;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.loader.ExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.SingleCellRnaSeqBaselineExperimentFactory;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class ScxaExperimentTrader extends ExperimentTrader {
    private final LoadingCache<String, BaselineExperiment> baselineExperimentsCache;

    @Inject
    public ScxaExperimentTrader(ScxaExperimentDao experimentDao,
                                SingleCellRnaSeqBaselineExperimentFactory
                                              experimentFactory,
                                ArrayExpressClient arrayExpressClient,
                                ExperimentDesignParser experimentDesignParser) {
        super(experimentDao);
        baselineExperimentsCache =
                CacheBuilder.newBuilder().build(
                        new ExperimentsCacheLoader<>(
                                arrayExpressClient, experimentDesignParser, experimentDao, experimentFactory));
    }

    public Experiment getPublicExperiment(String experimentAccession) {
        try {
            return baselineExperimentsCache.get(experimentAccession);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Experiment getExperiment(String experimentAccession, String accessKey) {
        return getPublicExperiment(experimentAccession);
    }

    public void removeExperimentFromCache(String experimentAccession) {
        baselineExperimentsCache.invalidate(experimentAccession);
    }

    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {
        if(experimentType.isSingleCell()) {
            return getPublicExperiment(experimentAccession);
        } else {
            return null;
        }
    }
}
