
package uk.ac.ebi.atlas.model.experiment.differential.rnaseq;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

public class RnaSeqProfile extends DifferentialProfile<DifferentialExpression, RnaSeqProfile> {


    public RnaSeqProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    @Override
    protected RnaSeqProfile createEmptyCopy() {
        return new RnaSeqProfile(getId(), getName());
    }

}