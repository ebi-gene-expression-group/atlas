package uk.ac.ebi.atlas.search.baseline;


import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.List;
import java.util.SortedSet;

public class BaselineExperimentSearchResult {
    final BaselineExperimentProfilesList experimentProfiles;
    final List<FactorAcrossExperiments> factorsAcrossAllExperiments;

    public BaselineExperimentSearchResult(BaselineExperimentProfilesList experimentProfiles, List<FactorAcrossExperiments>
            factorsAcrossAllExperiments) {
        this.experimentProfiles = experimentProfiles;
        this.factorsAcrossAllExperiments = factorsAcrossAllExperiments;
    }

    public boolean isEmpty() {
        return experimentProfiles.isEmpty();
    }

    public BaselineExperimentProfilesList getExperimentProfiles() {
        return experimentProfiles;
    }

    public List<FactorAcrossExperiments> getFactorsAcrossAllExperiments() {
        return factorsAcrossAllExperiments;
    }

}