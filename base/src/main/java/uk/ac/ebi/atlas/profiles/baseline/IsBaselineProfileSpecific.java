
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.io.Serializable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class IsBaselineProfileSpecific implements Predicate<BaselineProfile>, Serializable {

    private Set<Factor> selectedQueryFactors;

    private Set<Factor> allQueryFactors;

    public IsBaselineProfileSpecific(Set<Factor> selectedQueryFactors, Set<Factor> allQueryFactors) {
        checkArgument(!selectedQueryFactors.isEmpty(), "selectedQueryFactors is empty");
        checkArgument(!allQueryFactors.isEmpty(), "allQueryFactors is empty");
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;
    }

    @Override
    public boolean apply(BaselineProfile baselineProfile) {
        return isOverExpressedInSelectedQueryFactors(baselineProfile);
    }

    boolean isOverExpressedInSelectedQueryFactors(BaselineProfile baselineProfile) {

        double averageOnSelectedQueryFactors = baselineProfile.getAverageExpressionLevelOn(selectedQueryFactors);
        Set<Factor> nonSelectedQueryFactors = Sets.newHashSet(allQueryFactors);
        nonSelectedQueryFactors.removeAll(selectedQueryFactors);

        double maxOnNonSelectedQueryFactors = baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactors);

        return averageOnSelectedQueryFactors > maxOnNonSelectedQueryFactors;

    }

}
