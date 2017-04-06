package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

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
    public void hasDefaultProperties() {
        assertThat(subject.properties(), hasEntry("id", GENE_ID));
        assertThat(subject.properties(), hasEntry("name", GENE_NAME));
    }

    @Test
    public void hasDesignElementProperty() throws Exception {
        assertThat(subject.properties(), hasEntry("designElement", DESIGN_ELEMENT_NAME));
    }
}