package uk.ac.ebi.atlas.model;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;

import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Named
public class GeneExpressionPrecondition implements Predicate<Expression> {

    private double cutoff;

    private Set<FactorValue> limitingFactorValues = new HashSet<>();

    private String queryFactorType;

    public GeneExpressionPrecondition() {
    }

    public void setLimitingFactorValues(Set<FactorValue> limitingFactorValues) {
        this.limitingFactorValues = limitingFactorValues;
    }

    public void setCutoff(double cutoff) {
        this.cutoff = cutoff;
    }

    public String getQueryFactorType() {
        return queryFactorType;
    }

    public void setQueryFactorType(String queryFactorType) {
        this.queryFactorType = queryFactorType;
    }

    @Override
    public boolean apply(Expression expression) {

        return expression.isGreaterThan(cutoff)
                && checkLimitingFactors(expression);
    }

    protected boolean checkLimitingFactors(Expression expression) {
        return (CollectionUtils.isEmpty(limitingFactorValues)
        || expression.getAllFactorValues().containsAll(limitingFactorValues));
    }

    public Set<FactorValue> getLimitingFactorValues() {
        return limitingFactorValues;
    }
}
