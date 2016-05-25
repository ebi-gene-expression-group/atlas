package uk.ac.ebi.atlas.search.baseline;

import com.google.auto.value.AutoValue;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.SortedSet;

@AutoValue
public abstract class BaselineExperimentSlice {

    public static BaselineExperimentSlice create(BaselineExperiment experiment, FactorGroup filterFactors) {
        return new AutoValue_BaselineExperimentSlice(experiment, filterFactors);
    }

    public abstract BaselineExperiment experiment();
    public abstract FactorGroup filterFactors();

    // these will typically be the tissue (ie: ORGANISM_PART) factors for the slice
    public SortedSet<Factor> nonFilterFactors() {
        return experiment().getExperimentalFactors().getComplementFactors(filterFactors());
    }

    public String organism() {
        return experiment().getFirstOrganism();
    }

    public String experimentAccession() {
        return experiment().getAccession();
    }

    public String experimentDisplayName() {
        return experiment().getDisplayName();
    }

    public ExperimentType getExperimentType() {
        return experiment().getType();
    }

}
