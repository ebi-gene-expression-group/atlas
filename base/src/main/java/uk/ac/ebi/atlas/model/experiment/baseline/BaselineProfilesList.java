package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;

public class BaselineProfilesList extends GeneProfilesList<BaselineProfile> {

    public BaselineProfilesList() {
    }

    public BaselineProfilesList(Collection<BaselineProfile> collection) {
        super(collection);
    }
}
