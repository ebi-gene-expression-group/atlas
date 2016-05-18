
package uk.ac.ebi.atlas.search.baseline;


import uk.ac.ebi.atlas.model.baseline.GenericBaselineProfilesList;

import java.util.Collection;

public class BaselineExperimentProfilesList extends GenericBaselineProfilesList<BaselineExperimentProfile> {

    public BaselineExperimentProfilesList() {
    }

    public BaselineExperimentProfilesList(Collection<BaselineExperimentProfile> collection) {
        super(collection);
    }
}
