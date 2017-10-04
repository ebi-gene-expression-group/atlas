package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class IsDifferentialProfileSpecific<P extends DifferentialProfile<? extends DifferentialExpression, P>> implements Predicate<P> {

    private final Set<Contrast> selectedQueryContrasts;
    private final Sets.SetView<Contrast> nonSelectedQueryContrasts;

    public IsDifferentialProfileSpecific(Set<Contrast> selectedQueryContrasts, Set<Contrast> allQueryFactors) {
        checkArgument(!selectedQueryContrasts.isEmpty(),"selectedQueryContrasts is empty");
        checkArgument(!allQueryFactors.isEmpty(), "allQueryFactors is empty");

        this.selectedQueryContrasts = selectedQueryContrasts;
        this.nonSelectedQueryContrasts = Sets.difference(allQueryFactors, selectedQueryContrasts);
    }

    @Override
    public boolean apply(P differentialProfile) {
        return differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts) > differentialProfile.getMaxExpressionLevelOn(nonSelectedQueryContrasts);
    }

}
