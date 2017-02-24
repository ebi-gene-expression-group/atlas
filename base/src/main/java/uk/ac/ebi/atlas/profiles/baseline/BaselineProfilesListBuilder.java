package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;

import javax.inject.Named;

public class BaselineProfilesListBuilder implements GeneProfilesListBuilder<BaselineProfilesList> {

    @Override
    public BaselineProfilesList create() {
        return new BaselineProfilesList();
    }
}
