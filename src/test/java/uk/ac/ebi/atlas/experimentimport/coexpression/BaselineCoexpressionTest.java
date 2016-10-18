package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaselineCoexpressionTest {

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(
            BaselineCoexpression.create( 0.01, "GENE 2")
            .compareTo(BaselineCoexpression.create(0.1, "GENE 2")) < 0);
    }
}