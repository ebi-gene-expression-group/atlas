package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import com.google.common.base.Preconditions;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named
public class ExpressionSerializerService {

    private final ExperimentTrader experimentTrader;
    private final RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;
    private final ExperimentChecker experimentChecker;


    @Inject
    public ExpressionSerializerService(ExperimentTrader experimentTrader, RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer,
                                       ExperimentChecker experimentChecker) {
        this.experimentTrader = experimentTrader;
        this.rnaSeqBaselineExpressionKryoSerializer = rnaSeqBaselineExpressionKryoSerializer;
        this.experimentChecker = experimentChecker;
    }

    public String kryoSerializeExpressionData(String experimentAccession) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        Preconditions.checkState(experiment != null,
                "Experiment not found in cache, refusing to serialize:" + experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, experiment.getType());
        if (experiment.getType().isRnaSeqBaseline()) {
            return rnaSeqBaselineExpressionKryoSerializer.serializeExpressionData(experimentAccession,
                    ((BaselineExperiment) experiment).getExperimentalFactors());
        } else {
            return "skipped";
        }
    }
}
