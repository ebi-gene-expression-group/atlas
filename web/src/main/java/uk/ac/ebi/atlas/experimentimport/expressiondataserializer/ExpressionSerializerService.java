package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class ExpressionSerializerService {

    private ExperimentTrader experimentTrader;
    private ExpressionSerializerFactory expressionSerializerFactory;
    private BaselineExperimentsCache baselineExperimentsCache;


    @Inject
    public ExpressionSerializerService(ExperimentTrader experimentTrader, ExpressionSerializerFactory expressionSerializerFactory,
                                       BaselineExperimentsCache baselineExperimentsCache) {
        this.experimentTrader = experimentTrader;
        this.expressionSerializerFactory = expressionSerializerFactory;
        this.baselineExperimentsCache = baselineExperimentsCache;
    }

    public void kryoSerializeExpressionData(String experimentAccession) {
        try {
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
            expressionSerializerFactory.getKryoSerializer(experiment.getType())
                    .serializeExpressionData(experimentAccession, baselineExperimentsCache.getExperiment(experimentAccession).getExperimentalFactors());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

}
