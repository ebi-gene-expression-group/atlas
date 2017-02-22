package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.Set;

public interface BaselineProfileStreamOptions extends ProfileStreamOptions<AssayGroup> {
    String getExperimentAccession();
    double getCutoff();
    String getQueryFactorType();
    Double getThresholdForPremium();
    Double getFractionForPremium();
}
