package uk.ac.ebi.atlas.model.experiment.differential;

import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;

public class DifferentialProfilesList<T extends DifferentialProfile> extends GeneProfilesList<T> {

    public DifferentialProfilesList() {
        // default, with no starting collection
    }

    public DifferentialProfilesList(Collection<T> collection) {
        super(collection);
    }

}
