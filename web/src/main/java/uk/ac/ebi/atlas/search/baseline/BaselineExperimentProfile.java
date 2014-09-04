package uk.ac.ebi.atlas.search.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.Comparator;

public class BaselineExperimentProfile extends BaselineProfile implements Comparable<BaselineExperimentProfile> {

    private final FactorGroup filterFactors;

    private final Integer nonFilterFactorsSize;

    public BaselineExperimentProfile(BaselineExperimentSlice experimentSlice) {
        super(experimentSlice.experimentAccession(), experimentSlice.experimentDisplayName());
        filterFactors = experimentSlice.filterFactors();
        nonFilterFactorsSize = experimentSlice.nonFilterFactors().size();
    }

    public FactorGroup getFilterFactors() {
        return filterFactors;
    }

    @Override
    public int compareTo(BaselineExperimentProfile other) {
        return (other.nonFilterFactorsSize).compareTo(this.nonFilterFactorsSize);
    }

}
