package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

import static com.google.common.base.Preconditions.checkState;

// This is a reusable builder that can be called multiple times in a read loop.
// To start creating another instance call beginNewInstance
public class RnaSeqProfileReusableBuilder {

    private RnaSeqProfile profile;

    private Predicate<DifferentialExpression> expressionFilter;

    public RnaSeqProfileReusableBuilder(Predicate<DifferentialExpression> expressionFilter) {
        this.expressionFilter = expressionFilter;
    }

    public RnaSeqProfileReusableBuilder beginNewInstance(String geneId, String geneName) {
        profile = new RnaSeqProfile(geneId, geneName);
        return this;
    }

    public RnaSeqProfileReusableBuilder addExpression(DifferentialExpression expression) {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        if (expressionFilter.apply(expression)) {
            profile.add(expression);
        }
        return this;
    }

    public RnaSeqProfile create() {
        checkState(profile != null, "Please invoke beginNewInstance before create");
        return profile;
    }
}
