package uk.ac.ebi.atlas.model.differential;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DifferentialExpressionTest {


    @Test
    public void testUnderExpressedGeneIsForRegulation() throws Exception {
        //when
        DifferentialExpression expression = new DifferentialExpression(1.0, -1.0, null);

        //then
        assertThat(expression.isForRegulation(Regulation.UP_DOWN), is(true));
        assertThat(expression.isForRegulation(Regulation.UP), is(false));
        assertThat(expression.isForRegulation(Regulation.DOWN), is(true));
    }

    @Test
    public void testOverExpressedGeneIsForRegulation() throws Exception {
        //when
        DifferentialExpression expression = new DifferentialExpression(1.0, 1.0, null);

        //then
        assertThat(expression.isForRegulation(Regulation.UP_DOWN), is(true));
        assertThat(expression.isForRegulation(Regulation.UP), is(true));
        assertThat(expression.isForRegulation(Regulation.DOWN), is(false));
    }

}
