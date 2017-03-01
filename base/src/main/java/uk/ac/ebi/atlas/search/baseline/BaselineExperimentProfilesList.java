
package uk.ac.ebi.atlas.search.baseline;


import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.Collection;

public class BaselineExperimentProfilesList extends GeneProfilesList<BaselineExperimentProfile> {

    public BaselineExperimentProfilesList() {
    }

    public BaselineExperimentProfilesList(Collection<BaselineExperimentProfile> collection) {
        super(collection);
    }
}
