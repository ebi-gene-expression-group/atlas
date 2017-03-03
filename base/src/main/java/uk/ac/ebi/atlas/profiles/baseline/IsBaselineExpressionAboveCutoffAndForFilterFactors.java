package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
/*
I am being questionable in my logic to include only the columns that will later be picked up.
Unfortunately I affect the number of columns in a profile, that matters for "foldProfile" used in gene sets.
Feel free to rethink me or get rid of me if the gene set code is gone after all.o
 */
@Named
@Scope("prototype")
public class IsBaselineExpressionAboveCutoffAndForFilterFactors implements Predicate<BaselineExpression> {

    private double cutoff;
    private Collection<String> filterFactors = new HashSet<>();

    public IsBaselineExpressionAboveCutoffAndForFilterFactors() {
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setFilterFactors(Collection<AssayGroup> filterFactors) {
        this.filterFactors = FluentIterable.from(filterFactors).transform(new Function<AssayGroup, String>() {
            @Nullable
            @Override
            public String apply(@Nullable AssayGroup assayGroup) {
                return assayGroup.getId();
            }
        }).toSet();

        return this;
    }

    public IsBaselineExpressionAboveCutoffAndForFilterFactors setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    @Override
    public boolean apply(BaselineExpression expression) {
        return checkFilterFactors(expression) && (expression.isGreaterThanOrEqual(cutoff));
    }

    protected boolean checkFilterFactors(BaselineExpression expression) {
        return (CollectionUtils.isEmpty(filterFactors) || filterFactors.contains(expression.getDataColumnDescriptorId()));
    }

}
