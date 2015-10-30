package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.Set;

/**
 * Created by barrera on 26/10/2015.
 *
 */
public class IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors implements Predicate<BaselineExpression> {

    private double cutoff;

    private Set<ImmutableSet<Factor>> multiHeaderFactors = Sets.newLinkedHashSet();

    public IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors() {
    }

    public IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    public IsBaselineExpressionAboveCutoffAndAllMultiHeaderFactors setMultiHeaderFactors(Set<ImmutableSet<Factor>> multiHeaderFactors) {
        this.multiHeaderFactors = multiHeaderFactors;
        return this;
    }

    @Override
    public boolean apply(BaselineExpression expression) {
        return checkFilterFactors(expression) && (!expression.isKnown() || expression.isGreaterThan(cutoff));
    }

    protected boolean checkFilterFactors(BaselineExpression expression) {
        return (CollectionUtils.isEmpty(multiHeaderFactors) || expression.containsAllMultiheaderFactors(multiHeaderFactors));
    }
}
