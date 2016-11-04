package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;

import static com.google.common.base.Preconditions.checkState;

// This is a reusable builder that can be called multiple times in a read loop.
// To start creating another instance call beginNewInstance
public class MicroarrayProfileReusableBuilder {

    private MicroarrayProfile profile;

    private Predicate<DifferentialExpression> expressionFilter;

    public MicroarrayProfileReusableBuilder(Predicate<DifferentialExpression> expressionFilter) {
        this.expressionFilter = expressionFilter;
    }

    public MicroarrayProfileReusableBuilder beginNewInstance(String geneId, String geneName, String designElement) {
        profile = new MicroarrayProfile(geneId, geneName, designElement);
        return this;
    }

    public MicroarrayProfileReusableBuilder addExpression(MicroarrayExpression expression) {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        if (expressionFilter.apply(expression)) {
            profile.add(expression);
        }
        return this;
    }

    public MicroarrayProfile create() {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        return profile;
    }
}
