package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;

public class BaselineProfilesListBuilder implements GeneProfilesListBuilder<BaselineProfilesList> {

    @Override
    public BaselineProfilesList get() {
        return new BaselineProfilesList();
    }
}
