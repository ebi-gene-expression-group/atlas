
package uk.ac.ebi.atlas.model.experiment.differential.rnaseq;

import org.junit.Before;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

public class RnaSeqProfileTest {

    public static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "geneName";

    RnaSeqProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqProfile(GENE_ID, GENE_NAME);
    }

    /* this is a placeholder for future tests */
}