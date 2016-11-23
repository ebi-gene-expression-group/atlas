package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.text.MessageFormat;

@Named
public class ExpressionSerializerService {

    private final ExperimentTrader experimentTrader;
    private final RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;
    private final ExperimentChecker experimentChecker;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    String serializedExpressionsFileTemplate;

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

    public void removeKryoFile(String experimentAccession) {
        File f = new File(MessageFormat.format(serializedExpressionsFileTemplate, experimentAccession));
        if(f.exists()){
            f.delete();
        }
    }
}
