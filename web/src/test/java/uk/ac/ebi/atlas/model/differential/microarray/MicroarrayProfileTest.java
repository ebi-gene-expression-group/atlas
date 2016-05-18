
package uk.ac.ebi.atlas.model.differential.microarray;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MicroarrayProfileTest {

    public static final String DESIGN_ELEMENT_NAME = "designElementName";
    public static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "geneName";

    MicroarrayProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayProfile(GENE_ID, GENE_NAME, DESIGN_ELEMENT_NAME);
    }

    @Test
    public void testGetDesignElementName() throws Exception {
        assertThat(subject.getDesignElementName(), is(DESIGN_ELEMENT_NAME));
    }
}