package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineExpressionTest {

    private static final String EXPRESSION_LEVEL_1 = "0";
    private static final String EXPRESSION_LEVEL_2 = "123.4";

    @Test
    public void expressionLevel0 () {


        assertThat(new BaselineExpression(EXPRESSION_LEVEL_1, "g1").getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));
        assertThat(new BaselineExpression(EXPRESSION_LEVEL_2, "g1").getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

    }

    @Test
    public void expressionLevelNA () {
        assertThat(new BaselineExpression("0", "g1").getLevel(), is(new BaselineExpression("NA", "g1").getLevel()));
    }

    @Test
    public void testRemoveTrailingZero() {
        assertThat(BaselineExpression.removeTrailingZero(1.111111), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.100111), is("1.1001"));
        assertThat(BaselineExpression.removeTrailingZero(1.111100), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.110000), is("1.11"));
    }

}
