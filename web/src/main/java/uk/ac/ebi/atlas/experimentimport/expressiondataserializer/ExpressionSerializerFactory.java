package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class ExpressionSerializerFactory {

    private RnaSeqBaselineExpressionSerializer rnaSeqBaselineExpressionSerializer;

    @Inject
    public ExpressionSerializerFactory(RnaSeqBaselineExpressionSerializer rnaSeqBaselineExpressionSerializer) {
        this.rnaSeqBaselineExpressionSerializer = rnaSeqBaselineExpressionSerializer;
    }

    public ExpressionSerializer getSerializer(ExperimentType experimentType) {
        if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE) {
            return rnaSeqBaselineExpressionSerializer;
        }

        throw new UnsupportedOperationException("No expression serializer for experiment type " + experimentType);
    }
}
