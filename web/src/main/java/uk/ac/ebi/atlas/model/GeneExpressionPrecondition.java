package uk.ac.ebi.atlas.model;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;

import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Named
public class GeneExpressionPrecondition implements Predicate<Expression> {

    private double cutoff;

    private Set<Factor> limitingFactors = new HashSet<>();

    private String queryFactorType;

    public GeneExpressionPrecondition() {
    }

    public void setLimitingFactors(Set<Factor> limitingFactors) {
        this.limitingFactors = limitingFactors;
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
        return (CollectionUtils.isEmpty(limitingFactors)
        || expression.getAllFactors().containsAll(limitingFactors));
    }

    public Set<Factor> getLimitingFactors() {
        return limitingFactors;
    }
}
