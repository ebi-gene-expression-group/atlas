package uk.ac.ebi.atlas.model.differential;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Set;

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

    public DifferentialProfile add(T expression) {
        addExpression(expression.getContrast(), expression);
        return this;
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

    public double getStrongestExpressionLevelOn(Set<Contrast> queryContrasts) {
        double expressionLevel = DifferentialExpression.WEAKEST_LEVEL;

        for (Contrast condition : queryContrasts) {
            Double level = getKnownExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, Math.abs(level));
            }
        }
        return expressionLevel;
    }

    public double getAverageExpressionLevelOn(Set<Contrast> contrasts) {
        checkArgument(!CollectionUtils.isEmpty(contrasts),
                "This method must be invoked with all conditions when the set of selected conditions is empty");

        double expressionLevel = 0D;

        for (Contrast contrast : contrasts) {
            Double level = getKnownExpressionLevel(contrast);
            if (level != null) {
                expressionLevel += Math.abs(level);
            }
        }

        return expressionLevel / contrasts.size();
    }

    public double getAveragePValueOn(Set<Contrast> contrasts) {
        checkArgument(!CollectionUtils.isEmpty(contrasts),
                "This method must be invoked with all conditions when the set of selected conditions is empty");

        double pValueTotal = 0D;

        for (Contrast contrast : contrasts) {
            T expression = getExpression(contrast);
            if (expression != null && expression.isKnown()) {
                pValueTotal += expression.getPValue();
            } else {
                pValueTotal += MIN_P_VALUE;
            }
        }

        return pValueTotal / contrasts.size();
    }

    public String getDesignElementName(){
        return "";
    }

    @Override
    protected void addExpression(DifferentialExpression differentialExpression) {
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
