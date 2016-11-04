
package uk.ac.ebi.atlas.differential.rnaseq;

import uk.ac.ebi.atlas.differential.DifferentialExpression;
import uk.ac.ebi.atlas.differential.DifferentialProfile;

public class RnaSeqProfile extends DifferentialProfile<DifferentialExpression> {


    public RnaSeqProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

}