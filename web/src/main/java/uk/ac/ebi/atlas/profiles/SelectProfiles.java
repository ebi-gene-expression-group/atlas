package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

public interface SelectProfiles<T extends Profile, L extends GeneProfilesList<T>> {
    L select(Iterable<T> profiles, int maxSize);
}
