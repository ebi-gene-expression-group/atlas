
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;

import static com.google.common.base.Preconditions.checkState;

// This is a reusable builder that can be called multiple times in a read loop.
// To start creating another instance call beginNewInstance
public class BaselineProfileReusableBuilder {

    private final String queryFactorType;
    private BaselineProfile baselineProfile;

    private Predicate<BaselineExpression> baselineExpressionFilter;

    public BaselineProfileReusableBuilder(Predicate<BaselineExpression> baselineExpressionFilter, String queryFactorType) {
        this.baselineExpressionFilter = baselineExpressionFilter;
        this.queryFactorType = queryFactorType;
    }

    public BaselineProfileReusableBuilder beginNewInstance(String geneId, String geneName) {
        baselineProfile = new BaselineProfile(geneId, geneName);
        return this;
    }

    public BaselineProfileReusableBuilder addExpression(BaselineExpression expression) {
        checkState(baselineProfile != null, "Please invoke beginNewInstance before create");
        if (baselineExpressionFilter.apply(expression)) {
            baselineProfile.add(queryFactorType, expression);
        }
        return this;
    }

    public BaselineProfile create() {
        checkState(baselineProfile != null, "Please invoke beginNewInstance before create");
        return baselineProfile;
    }
}