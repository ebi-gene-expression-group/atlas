package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

// Consider moving me to ExperimentCrud, I am coupled to it anyway - through accepting ExperimentDTO in API
@Named
public class ExpressionSerializerService {

    private final RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;
    private final ExperimentChecker experimentChecker;

    @Inject
    public ExpressionSerializerService(RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer,
                                       ExperimentChecker experimentChecker) {
        this.rnaSeqBaselineExpressionKryoSerializer = rnaSeqBaselineExpressionKryoSerializer;
        this.experimentChecker = experimentChecker;
    }

    public String kryoSerializeExpressionData(ExperimentDTO experimentDTO) {
        return kryoSerializeExpressionData(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType());
    }

    String kryoSerializeExpressionData(String experimentAccession, ExperimentType experimentType) {
        experimentChecker.checkAllFiles(experimentAccession, experimentType);
        if (experimentType.isRnaSeqBaseline()) {
            String tpmsMessage = rnaSeqBaselineExpressionKryoSerializer.serializeExpressionData(experimentAccession, ExpressionUnit.Absolute.Rna.TPM);
            String fpkmsMessage = rnaSeqBaselineExpressionKryoSerializer.serializeExpressionData(experimentAccession, ExpressionUnit.Absolute.Rna.FPKM);
            return MessageFormat.format("TPMs: {0}, FPKMs: {1}", tpmsMessage, fpkmsMessage);
        } else {
            return "skipped";
        }
    }

    public void removeKryoFile(String experimentAccession) {
        rnaSeqBaselineExpressionKryoSerializer.delete(experimentAccession, ExpressionUnit.Absolute.Rna.TPM);
        rnaSeqBaselineExpressionKryoSerializer.delete(experimentAccession, ExpressionUnit.Absolute.Rna.FPKM);
    }
}
