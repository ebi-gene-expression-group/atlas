package uk.ac.ebi.atlas.search.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

public class BaselineExperimentProfile extends BaselineProfile implements Comparable<BaselineExperimentProfile> {

    private final FactorGroup filterFactors;

    private final Integer nonFilterFactorsSize;

    private FilterFactorsConverter filterFactorsConverter = new FilterFactorsConverter();


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
        int comparison = (other.nonFilterFactorsSize).compareTo(this.nonFilterFactorsSize);
        return (comparison != 0) ? comparison : (this.getName().compareTo(other.getName()));
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getName());
        if (!filterFactors.isEmpty() && !filterFactors.containsOnlyOrganism()) {
            sb.append(" - ").append(filterFactorsConverter.prettyPrint(filterFactors));
        }
        return sb.toString();
    }
}
