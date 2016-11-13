package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class IsDifferentialProfileSpecific<P extends DifferentialProfile> implements Predicate<P> {

    private final Set<?> selectedQueryContrasts;
    private final Sets.SetView<?> nonSelectedQueryContrasts;

    public IsDifferentialProfileSpecific(Set<?> selectedQueryContrasts, Set<?> allQueryFactors) {
        checkArgument(!selectedQueryContrasts.isEmpty(),"selectedQueryContrasts is empty");
        checkArgument(!allQueryFactors.isEmpty(), "allQueryFactors is empty");

        this.selectedQueryContrasts = selectedQueryContrasts;
        this.nonSelectedQueryContrasts = Sets.difference(allQueryFactors, selectedQueryContrasts);
    }

    @Override
    public boolean apply(P differentialProfile) {
        return differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts) > differentialProfile.getStrongestExpressionLevelOn(nonSelectedQueryContrasts);
    }

}
