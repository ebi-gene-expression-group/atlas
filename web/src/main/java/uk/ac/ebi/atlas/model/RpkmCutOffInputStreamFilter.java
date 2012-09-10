package uk.ac.ebi.atlas.model;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.commons.ObjectInputStreamFilter;

public class RpkmCutOffInputStreamFilter extends ObjectInputStreamFilter<ExpressionLevel> {

    private static double DEFAULT_RPKM_CUT_OFF_VALUE = 0D;

    private double rpkmCutOffValue = DEFAULT_RPKM_CUT_OFF_VALUE;

    public RpkmCutOffInputStreamFilter(ObjectInputStream<ExpressionLevel> expressionLevelObjectInputStream) {
        super(expressionLevelObjectInputStream);
    }

    public RpkmCutOffInputStreamFilter setRpkmCutOffValue(double rpkmCutOffValue){
        this.rpkmCutOffValue = rpkmCutOffValue;
        return this;
    }

    @Override
    protected Predicate<ExpressionLevel> getAcceptanceCriteria() {
        return new Predicate<ExpressionLevel>(){

            @Override
            public boolean apply(ExpressionLevel expressionLevel) {
                return expressionLevel.getRpkm() >= rpkmCutOffValue;
            }
        };
    }


}
