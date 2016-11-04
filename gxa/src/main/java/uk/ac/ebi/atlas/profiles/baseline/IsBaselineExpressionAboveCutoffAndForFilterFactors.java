package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import com.google.common.base.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Named
@Scope("prototype")
public class IsBaselineExpressionAboveCutoffAndForFilterFactors implements Predicate<BaselineExpression> {

    private double cutoff;
    private Set<Factor> filterFactors = new HashSet<>();

    public IsBaselineExpressionAboveCutoffAndForFilterFactors() {
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setFilterFactors(Set<Factor> filterFactors) {
        this.filterFactors = filterFactors;
        return this;
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    @Override
    public boolean apply(BaselineExpression expression) {
        return checkFilterFactors(expression) && (!expression.isKnown() || expression.isGreaterThan(cutoff));
    }

    protected boolean checkFilterFactors(BaselineExpression expression) {
        return (CollectionUtils.isEmpty(filterFactors) || expression.containsAll(filterFactors));
    }

}
