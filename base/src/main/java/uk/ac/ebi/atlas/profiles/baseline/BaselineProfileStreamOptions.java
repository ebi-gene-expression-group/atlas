package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.baseline.Factor;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.Set;

public interface BaselineProfileStreamOptions extends ProfileStreamOptions<Factor> {
    String getExperimentAccession();
    double getCutoff();
    String getQueryFactorType();
    Set<Factor> getSelectedFilterFactors();
    Double getThresholdForPremium();
    Double getFractionForPremium();
}
