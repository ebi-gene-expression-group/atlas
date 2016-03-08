package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 07/03/2016.
 */
public class BaselineCoexpressionTest {



    @Test
    public void testCompareTo() throws Exception {
        assertTrue(
            BaselineCoexpression.create("GENE 1", 0.01, "GENE 2")
            .compareTo(BaselineCoexpression.create("GENE 1", 0.1, "GENE 2")) < 0);
    }
}