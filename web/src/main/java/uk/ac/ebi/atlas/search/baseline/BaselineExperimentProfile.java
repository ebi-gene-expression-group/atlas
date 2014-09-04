package uk.ac.ebi.atlas.search.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

public class BaselineExperimentProfile extends BaselineProfile {

    private final FactorGroup filterFactors;

    public BaselineExperimentProfile(BaselineExperimentSlice experimentSlice) {
        super(experimentSlice.experimentAccession(), experimentSlice.experimentDisplayName());
        filterFactors = experimentSlice.filterFactors();
    }

    public FactorGroup getFilterFactors() {
        return filterFactors;
    }
}
