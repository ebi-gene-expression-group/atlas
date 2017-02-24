package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.profiles.ProfileStreamTransforms;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

public class DifferentialProfileStreamTransforms<Prof extends DifferentialProfile<? extends DifferentialExpression>>
        extends ProfileStreamTransforms<Contrast, Prof> {

    public DifferentialProfileStreamTransforms(DifferentialProfileStreamOptions options, GeneQueryResponse geneQueryResponse){
        if (!geneQueryResponse.getAllGeneIds().isEmpty()) {
            register(keepOnlyProfilesWithGeneIds(geneQueryResponse.getAllGeneIds()));
        }
        if (!options.getSelectedQueryFactors().isEmpty()) {
            if (options.isSpecific()) {
                register(keepOnlyProfilesOverExpressedOnColumns(options.getSelectedQueryFactors(), options.getAllQueryFactors()));
            } else {
                register(keepOnlyProfilesExpressedOnColumns(options.getSelectedQueryFactors()));
            }
        }
    }
}
