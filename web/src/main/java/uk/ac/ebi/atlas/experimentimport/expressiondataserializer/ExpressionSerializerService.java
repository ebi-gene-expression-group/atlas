package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class ExpressionSerializerService {

    private final ExperimentTrader experimentTrader;
    private final ExpressionSerializerFactory expressionSerializerFactory;
    private final BaselineExperimentsCache baselineExperimentsCache;
    private final ExperimentChecker experimentChecker;


    @Inject
    public ExpressionSerializerService(ExperimentTrader experimentTrader, ExpressionSerializerFactory expressionSerializerFactory,
                                       BaselineExperimentsCache baselineExperimentsCache,ExperimentChecker experimentChecker) {
        this.experimentTrader = experimentTrader;
        this.expressionSerializerFactory = expressionSerializerFactory;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.experimentChecker = experimentChecker;
    }

    public void kryoSerializeExpressionData(String experimentAccession) {
        try {
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
            experimentChecker.checkAllFiles(experimentAccession, experiment.getType());
            expressionSerializerFactory.getKryoSerializer(experiment.getType())
                    .serializeExpressionData(experimentAccession, baselineExperimentsCache.getExperiment(experimentAccession).getExperimentalFactors());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

}
