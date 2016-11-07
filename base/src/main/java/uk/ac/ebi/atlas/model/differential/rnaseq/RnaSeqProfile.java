
package uk.ac.ebi.atlas.model.differential.rnaseq;

import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

public class RnaSeqProfile extends DifferentialProfile<DifferentialExpression> {


    public RnaSeqProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

}