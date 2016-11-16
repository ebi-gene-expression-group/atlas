package uk.ac.ebi.atlas.search.baseline;


import com.google.common.collect.ImmutableSortedSet;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.SortedSet;

public class BaselineExperimentSearchResult {
    final BaselineExperimentProfilesList experimentProfiles;
    final SortedSet<Factor> factorsAcrossAllExperiments;

    public BaselineExperimentSearchResult() {
        experimentProfiles = new BaselineExperimentProfilesList();
        factorsAcrossAllExperiments = ImmutableSortedSet.of();
    }

    public BaselineExperimentSearchResult(BaselineExperimentProfilesList experimentProfiles, SortedSet<Factor> factorsAcrossAllExperiments) {
        this.experimentProfiles = experimentProfiles;
        this.factorsAcrossAllExperiments = factorsAcrossAllExperiments;
    }

    public boolean isEmpty() {
        return experimentProfiles.isEmpty();
    }

    public BaselineExperimentProfilesList getExperimentProfiles() {
        return experimentProfiles;
    }

    public SortedSet<Factor> getFactorsAcrossAllExperiments() {
        return factorsAcrossAllExperiments;
    }

    public boolean containsFactorOfType(String type) {
        for (Factor factor : factorsAcrossAllExperiments) {
            if (factor.getType().equals(type)) return true;
        }

        return false;
    }
}