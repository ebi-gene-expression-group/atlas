package uk.ac.ebi.atlas.search.baseline;


import com.google.common.collect.ImmutableSortedSet;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.SortedSet;

public class BaselineTissueExperimentSearchResult {
    final BaselineProfilesList experimentProfiles;
    final SortedSet<Factor> supersetOfFactorsAcrossAllExperiments;

    public BaselineTissueExperimentSearchResult() {
        experimentProfiles = new BaselineProfilesList();
        supersetOfFactorsAcrossAllExperiments = ImmutableSortedSet.of();
    }

    public BaselineTissueExperimentSearchResult(BaselineProfilesList experimentProfiles, SortedSet<Factor> supersetOfFactorsAcrossAllExperiments) {
        this.experimentProfiles = experimentProfiles;
        this.supersetOfFactorsAcrossAllExperiments = supersetOfFactorsAcrossAllExperiments;
    }

    public boolean isEmpty() {
        return experimentProfiles.isEmpty();
    }

    public BaselineProfilesList getExperimentProfiles() {
        return experimentProfiles;
    }

    public SortedSet<Factor> getSupersetOfFactorsAcrossAllExperiments() {
        return supersetOfFactorsAcrossAllExperiments;
    }
}