package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.GeneProfilesList;

public interface GeneProfilesListBuilder<L extends GeneProfilesList> {

    L create();

}
