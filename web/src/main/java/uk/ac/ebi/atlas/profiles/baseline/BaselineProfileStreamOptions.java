package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.Set;

public interface BaselineProfileStreamOptions extends ProfileStreamOptions<Factor> {

    String getExperimentAccession();

    double getCutoff();

    String getQueryFactorType();

    Set<Factor> getSelectedFilterFactors();

}
