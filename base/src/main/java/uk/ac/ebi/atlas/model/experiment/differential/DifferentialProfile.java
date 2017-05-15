package uk.ac.ebi.atlas.model.experiment.differential;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class DifferentialProfile<T extends DifferentialExpression> extends Profile<Contrast, T> implements DifferentialExpressionLimits {

    private static final double MIN_P_VALUE = 1;

    private Double maxUpRegulatedExpressionLevel;
    private Double minUpRegulatedExpressionLevel;
    private Double maxDownRegulatedExpressionLevel;
    private Double minDownRegulatedExpressionLevel;

    private int downRegulatedExpressionsCount;
    private int upRegulatedExpressionsCount;

    public DifferentialProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    public double getMaxUpRegulatedExpressionLevel() {
        return maxUpRegulatedExpressionLevel == null ? Double.NaN : maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel() {
        return minUpRegulatedExpressionLevel == null ? Double.NaN : minUpRegulatedExpressionLevel;
    }

    public double getMaxDownRegulatedExpressionLevel() {
        return maxDownRegulatedExpressionLevel == null ? Double.NaN : maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel() {
        return minDownRegulatedExpressionLevel == null ? Double.NaN : minDownRegulatedExpressionLevel;
    }

    public int getSpecificity(Regulation regulation) {
        //TODO: these counts will be incorrect if adding more than 1 expression for the same contrast
        if(Regulation.UP == regulation){
            return upRegulatedExpressionsCount;
        }
        if(Regulation.DOWN == regulation){
            return downRegulatedExpressionsCount;
        }
        return getSpecificity();
    }

    public double getAveragePValueOn(Collection<Contrast> contrasts) {
        checkArgument(!CollectionUtils.isEmpty(contrasts),
                "This method must be invoked with all conditions when the set of selected conditions is empty");

        double pValueTotal = 0D;

        for (Contrast contrast : contrasts) {
            T expression = getExpression(contrast);
            if (expression != null ) {
                pValueTotal += expression.getPValue();
            } else {
                pValueTotal += MIN_P_VALUE;
            }
        }

        return pValueTotal / contrasts.size();
    }

    @Override
    protected void updateStateAfterAddingExpression(DifferentialExpression differentialExpression) {
        if (differentialExpression.isOverExpressed()) {
            addUpRegulatedExpression(differentialExpression.getLevel());
        } else if (differentialExpression.isUnderExpressed()) {
            addDownRegulatedExpression(differentialExpression.getLevel());
        }
    }

    private void addUpRegulatedExpression(double expressionLevel) {
        maxUpRegulatedExpressionLevel = (maxUpRegulatedExpressionLevel == null) ? expressionLevel : max(maxUpRegulatedExpressionLevel, expressionLevel);
        minUpRegulatedExpressionLevel = (minUpRegulatedExpressionLevel == null) ? expressionLevel : min(minUpRegulatedExpressionLevel, expressionLevel);
        upRegulatedExpressionsCount++;
    }

    private void addDownRegulatedExpression(double expressionLevel) {
        maxDownRegulatedExpressionLevel = (maxDownRegulatedExpressionLevel == null) ? expressionLevel : -max(Math.abs(maxDownRegulatedExpressionLevel), Math.abs(expressionLevel));
        minDownRegulatedExpressionLevel = (minDownRegulatedExpressionLevel == null) ? expressionLevel : -min(Math.abs(minDownRegulatedExpressionLevel), Math.abs(expressionLevel));
        downRegulatedExpressionsCount++;
    }

}
