package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import javax.inject.Named;
import java.util.function.Predicate;

/*
I am being questionable in my logic to include only the columns that will later be picked up.
Unfortunately I affect the number of columns in a profile, that matters for "foldProfile" used in gene sets.
Feel free to rethink me or get rid of me if the gene set code is gone after all.

Update: the gene set code is gone. Now you only need to rethink me ! :)

I used to have stuff about filter factors, didn't use it, because now we filter on the level of profile. Better design!
 */
@Named
@Scope("prototype")
public class IsBaselineExpressionAboveCutoff implements Predicate<BaselineExpression> {

    private double cutoff;

    public IsBaselineExpressionAboveCutoff setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    @Override
    public boolean test(BaselineExpression expression) {
        return (expression.isGreaterThanOrEqual(cutoff));
    }

}
