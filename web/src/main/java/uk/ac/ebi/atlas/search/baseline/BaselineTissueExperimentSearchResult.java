package uk.ac.ebi.atlas.search.baseline;


import com.google.common.collect.ImmutableSortedSet;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.SortedSet;

public class BaselineTissueExperimentSearchResult {
    final BaselineExperimentProfilesList experimentProfiles;
    final SortedSet<Factor> tissueFactorsAcrossAllExperiments;

    public BaselineTissueExperimentSearchResult() {
        experimentProfiles = new BaselineExperimentProfilesList();
        tissueFactorsAcrossAllExperiments = ImmutableSortedSet.of();
    }

    public BaselineTissueExperimentSearchResult(BaselineExperimentProfilesList experimentProfiles, SortedSet<Factor> tissueFactorsAcrossAllExperiments) {
        this.experimentProfiles = experimentProfiles;
        this.tissueFactorsAcrossAllExperiments = tissueFactorsAcrossAllExperiments;
    }

    public boolean isEmpty() {
        return experimentProfiles.isEmpty();
    }

    public BaselineExperimentProfilesList getExperimentProfiles() {
        return experimentProfiles;
    }

    public SortedSet<Factor> getTissueFactorsAcrossAllExperiments() {
        return tissueFactorsAcrossAllExperiments;
    }
}