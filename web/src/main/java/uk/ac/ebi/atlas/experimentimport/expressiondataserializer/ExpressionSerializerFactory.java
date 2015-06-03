package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class ExpressionSerializerFactory {

    private RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;

    @Inject
    public ExpressionSerializerFactory(RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer) {
        this.rnaSeqBaselineExpressionKryoSerializer = rnaSeqBaselineExpressionKryoSerializer;
    }

    public ExpressionSerializer getKryoSerializer(ExperimentType experimentType) {
        if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE) {
            return rnaSeqBaselineExpressionKryoSerializer;
        }

        throw new UnsupportedOperationException("No expression serializer for experiment type " + experimentType);
    }
}
