package uk.ac.ebi.atlas.trader;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
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
public class SingleCellExperimentTrader extends ExperimentTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellExperimentTrader.class);

    private final LoadingCache<String, BaselineExperiment> baselineExperimentsCache;
    @Inject
    public SingleCellExperimentTrader(ExperimentDAO experimentDAO,
                                      SingleCellRnaSeqBaselineExperimentFactory
                                              experimentFactory,
                                      ArrayExpressClient arrayExpressClient,
                                      ExperimentDesignParser experimentDesignParser) {
        super(experimentDAO);
        baselineExperimentsCache = CacheBuilder.newBuilder().build(new ExperimentsCacheLoader<>(arrayExpressClient,
                experimentDesignParser, experimentDAO, experimentFactory));
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

    public void removeAllExperimentsFromCache(){
        baselineExperimentsCache.invalidateAll();
    }

    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {
        if(experimentType.isSingleCell()) {
            return getPublicExperiment(experimentAccession);
        } else {
            return null;
        }
    }

    @Override
    protected void logError(Exception e) {
        LOGGER.error(e.getMessage());
    }
}
