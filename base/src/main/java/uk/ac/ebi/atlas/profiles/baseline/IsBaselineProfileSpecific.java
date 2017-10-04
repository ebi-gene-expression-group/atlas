
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class IsBaselineProfileSpecific implements Predicate<BaselineProfile>, Serializable {

    private Set<AssayGroup> selectedQueryFactors;

    private Collection<AssayGroup> allQueryFactors;

    public IsBaselineProfileSpecific(Set<AssayGroup> selectedQueryFactors, Collection<AssayGroup> allQueryFactors) {
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
        Set<AssayGroup> nonSelectedQueryFactors = Sets.newHashSet(allQueryFactors);
        nonSelectedQueryFactors.removeAll(selectedQueryFactors);

        double maxOnNonSelectedQueryFactors = baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactors);

        return averageOnSelectedQueryFactors > maxOnNonSelectedQueryFactors;

    }

}
