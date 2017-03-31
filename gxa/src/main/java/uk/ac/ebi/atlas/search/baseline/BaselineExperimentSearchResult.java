package uk.ac.ebi.atlas.search.baseline;

import uk.ac.ebi.atlas.model.FactorAcrossExperiments;

import java.util.List;

public class BaselineExperimentSearchResult {
    private final BaselineExperimentProfilesList experimentProfiles;
    private final List<FactorAcrossExperiments> factorsAcrossAllExperiments;

    public BaselineExperimentSearchResult(BaselineExperimentProfilesList experimentProfiles,
                                          List<FactorAcrossExperiments> factorsAcrossAllExperiments) {
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
