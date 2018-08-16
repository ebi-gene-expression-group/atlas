package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BaselineCoexpressionTest {
    @Test
    void testCompareTo() {
        assertTrue(
                BaselineCoexpression.create(0.01, "GENE 2")
                        .compareTo(BaselineCoexpression.create(0.1, "GENE 2")) < 0);
    }
}
