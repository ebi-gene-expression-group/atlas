package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.RankProfiles;

public interface RankDifferentialProfilesFactory<T extends DifferentialProfile> {

    RankProfiles<T, DifferentialProfilesList<T>> create(DifferentialProfileStreamOptions options);
}
