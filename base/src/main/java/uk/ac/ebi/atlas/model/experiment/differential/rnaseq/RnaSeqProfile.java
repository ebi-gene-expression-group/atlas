
package uk.ac.ebi.atlas.model.experiment.differential.rnaseq;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

public class RnaSeqProfile extends DifferentialProfile<DifferentialExpression> {


    public RnaSeqProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

}