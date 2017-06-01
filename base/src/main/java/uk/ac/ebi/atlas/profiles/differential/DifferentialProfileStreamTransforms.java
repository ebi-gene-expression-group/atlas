package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamTransforms;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

public class DifferentialProfileStreamTransforms<Prof extends DifferentialProfile<? extends DifferentialExpression, Prof>>
        extends ProfileStreamTransforms<Contrast, Prof> {

    public DifferentialProfileStreamTransforms(DifferentialProfileStreamOptions options, GeneQueryResponse geneQueryResponse) {
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

