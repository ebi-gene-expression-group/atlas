package uk.ac.ebi.atlas.streams;

import uk.ac.ebi.atlas.model.GeneProfilesList;

public interface GeneProfilesListBuilder<L extends GeneProfilesList> {

    L create();

}
