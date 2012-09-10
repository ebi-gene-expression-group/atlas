package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;

public class RpkmCutOffInputStreamFilter extends ObjectInputStreamFilter<TranscriptExpressionLevel> {

    private static double DEFAULT_RPKM_CUT_OFF_VALUE = 0D;

    private double rpkmCutOffValue = DEFAULT_RPKM_CUT_OFF_VALUE;

    public RpkmCutOffInputStreamFilter(ObjectInputStream<TranscriptExpressionLevel> expressionLevelObjectInputStream) {
        super(expressionLevelObjectInputStream);
    }

    public RpkmCutOffInputStreamFilter setRpkmCutOffValue(double rpkmCutOffValue) {
        this.rpkmCutOffValue = rpkmCutOffValue;
        return this;
    }

    @Override
    protected Predicate<TranscriptExpressionLevel> getAcceptanceCriteria() {
        return new Predicate<TranscriptExpressionLevel>() {

            @Override
            public boolean apply(TranscriptExpressionLevel transcriptExpressionLevel) {
                return transcriptExpressionLevel.getRpkm() > rpkmCutOffValue;
            }
        };
    }


}
