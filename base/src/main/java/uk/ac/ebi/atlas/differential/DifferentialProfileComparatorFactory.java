package uk.ac.ebi.atlas.differential;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

import javax.inject.Named;

@Named
@Scope("singleton")
public class DifferentialProfileComparatorFactory<T extends DifferentialProfile> {

    public DifferentialProfileComparator<T> create (DifferentialProfileStreamOptions options) {
        return new DifferentialProfileComparator<>(options.isSpecific(),
                options.getSelectedQueryFactors(),
                options.getAllQueryFactors(),
                options.getRegulation());
    }
}
