package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamTransforms;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

public class BaselineProfileStreamTransforms extends ProfileStreamTransforms<AssayGroup, BaselineProfile> {


    public BaselineProfileStreamTransforms(BaselineProfileStreamOptions options,
                                           GeneQueryResponse geneQueryResponse) {
        if (!geneQueryResponse.getAllGeneIds().isEmpty()) {
            register(keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds()));
        }
        if (options.isSpecific() && !options.getDataColumnsToReturn().equals(options.getAllDataColumns())) {
            register(keepOnlyProfilesOverExpressedOnColumns(options.getDataColumnsToReturn(), options.getAllDataColumns()));
        } else {
            register(keepOnlyProfilesExpressedOnColumns(options.getDataColumnsToReturn()));
        }
    }
}
