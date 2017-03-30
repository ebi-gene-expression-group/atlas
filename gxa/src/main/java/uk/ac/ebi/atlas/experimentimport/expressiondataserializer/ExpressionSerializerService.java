package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.text.MessageFormat;
/*
Consider moving me to ExperimentCrud, I am coupled to it anyway - through accepting ExperimentDTO in API
 */
@Named
public class ExpressionSerializerService {

    private final RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;
    private final ExperimentChecker experimentChecker;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    String serializedExpressionsFileTemplate;

    @Inject
    public ExpressionSerializerService(RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer,
                                       ExperimentChecker experimentChecker) {
        this.rnaSeqBaselineExpressionKryoSerializer = rnaSeqBaselineExpressionKryoSerializer;
        this.experimentChecker = experimentChecker;
    }

    public String kryoSerializeExpressionData(ExperimentDTO experimentDTO){
        return kryoSerializeExpressionData(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType());
    }

    String kryoSerializeExpressionData(String experimentAccession, ExperimentType experimentType) {
        experimentChecker.checkAllFiles(experimentAccession, experimentType);
        if (experimentType.isRnaSeqBaseline()) {
            return rnaSeqBaselineExpressionKryoSerializer.serializeExpressionData(experimentAccession
            );
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
