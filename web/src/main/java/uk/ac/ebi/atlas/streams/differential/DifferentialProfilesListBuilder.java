package uk.ac.ebi.atlas.streams.differential;

import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.streams.GeneProfilesListBuilder;

import javax.inject.Named;

@Named
public class DifferentialProfilesListBuilder implements GeneProfilesListBuilder<DifferentialProfilesList> {

    @Override
    public DifferentialProfilesList<DifferentialProfile> create() {
        return new DifferentialProfilesList<>();
    }
}
