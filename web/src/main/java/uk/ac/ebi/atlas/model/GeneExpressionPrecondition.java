package uk.ac.ebi.atlas.model;

import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;

import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Named
public class GeneExpressionPrecondition implements Predicate<Expression> {

    private double cutoff;

    private Set<Factor> filterFactors = new HashSet<>();

    private String queryFactorType;

    private Set<Factor> selectedQueryFactors;

    private boolean isSpecific;

    public GeneExpressionPrecondition() {
    }

    public void setFilterFactors(Set<Factor> filterFactors) {
        this.filterFactors = filterFactors;
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

    public Set<Factor> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    public boolean isSpecific() {
        return isSpecific;
    }

    public void setSpecific(boolean specific) {
        isSpecific = specific;
    }

    public void setSelectedQueryFactors(Set<Factor> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    @Override
    public boolean apply(Expression expression) {

        return expression.isGreaterThan(cutoff)
                && checkFilterFactors(expression);
    }

    protected boolean checkFilterFactors(Expression expression) {
        return (CollectionUtils.isEmpty(filterFactors)
        || expression.containsAll(filterFactors));
    }

    public Set<Factor> getFilterFactors() {
        return filterFactors;
    }
}
