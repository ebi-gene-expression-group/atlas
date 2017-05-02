package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

public interface BaselineProfileStreamOptions extends ProfileStreamOptions<AssayGroup> {
    String getExperimentAccession();
    double getCutoff();
    String getQueryFactorType();
}
