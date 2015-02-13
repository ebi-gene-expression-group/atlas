package uk.ac.ebi.atlas.bioentity.go;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GoPoTermTest {
    @Test
    public void GoPoTermWithAccessionAndNameAndDepth() {
        GoPoTerm goPoTerm = GoPoTerm.create("GO:0006950", "response to stress", 3);
        assertThat(goPoTerm.accession(), is("GO:0006950"));
        assertThat(goPoTerm.name(), is("response to stress"));
        assertThat(goPoTerm.depth(), is(3));

    }

}