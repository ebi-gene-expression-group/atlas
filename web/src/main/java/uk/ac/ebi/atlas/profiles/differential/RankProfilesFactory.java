package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.RankProfiles;

public interface RankProfilesFactory<P extends Profile, L extends GeneProfilesList<P>, O extends ProfileStreamOptions> {

    RankProfiles<P, L> create(O options);
}
