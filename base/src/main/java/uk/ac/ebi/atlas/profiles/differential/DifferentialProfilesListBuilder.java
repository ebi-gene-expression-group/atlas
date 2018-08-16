package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;

import javax.inject.Named;

@Named
public class DifferentialProfilesListBuilder<P extends DifferentialProfile>
             implements GeneProfilesListBuilder<DifferentialProfilesList<P>> {
    @Override
    public DifferentialProfilesList<P> get() {
        return new DifferentialProfilesList<>();
    }
}
