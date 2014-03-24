package uk.ac.ebi.atlas.model.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;

import javax.inject.Named;

@Named
@Scope("singleton")
public class BaselineProfileComparatorFactory {

    public BaselineProfileComparator create (BaselineProfileStreamOptions options) {
        return new BaselineProfileComparator(options.isSpecific(),
                options.getSelectedQueryFactors(),
                options.getAllQueryFactors(),
                options.getCutoff());
    }
}
