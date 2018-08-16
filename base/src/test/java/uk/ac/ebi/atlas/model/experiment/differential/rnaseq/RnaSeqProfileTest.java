package uk.ac.ebi.atlas.model.experiment.differential.rnaseq;

import org.junit.Before;

public class RnaSeqProfileTest {
    private static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "geneName";

    private RnaSeqProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqProfile(GENE_ID, GENE_NAME);
    }

    /* this is a placeholder for future tests */
}
