package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;
import uk.ac.ebi.atlas.model.TranscriptExpression;

public class RpkmCutOffInputStreamFilter extends ObjectInputStreamFilter<TranscriptExpression> {

    private static double DEFAULT_RPKM_CUT_OFF_VALUE = 0D;

    private double rpkmCutOffValue = DEFAULT_RPKM_CUT_OFF_VALUE;

    public RpkmCutOffInputStreamFilter(ObjectInputStream<TranscriptExpression> bjectInputStream) {
        super(bjectInputStream);
    }

    public RpkmCutOffInputStreamFilter setRpkmCutOffValue(double rpkmCutOffValue) {
        this.rpkmCutOffValue = rpkmCutOffValue;
        return this;
    }

    @Override
    protected Predicate<TranscriptExpression> getAcceptanceCriteria() {
        return new Predicate<TranscriptExpression>() {

            @Override
            public boolean apply(TranscriptExpression transcriptExpression) {
                return transcriptExpression.getRpkm() > rpkmCutOffValue;
            }
        };
    }


}
