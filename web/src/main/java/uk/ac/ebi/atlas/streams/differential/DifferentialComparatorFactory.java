package uk.ac.ebi.atlas.streams.differential;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparator;

import javax.inject.Named;

@Named
@Scope("singleton")
public class DifferentialComparatorFactory<T extends DifferentialProfile> {

    public DifferentialProfileComparator<T> create (DifferentialProfilesCommandOptions options) {
        return new DifferentialProfileComparator<>(options.isSpecific(),
                options.getSelectedQueryFactors(),
                options.getAllQueryFactors(),
                options.getRegulation());
    }
}
