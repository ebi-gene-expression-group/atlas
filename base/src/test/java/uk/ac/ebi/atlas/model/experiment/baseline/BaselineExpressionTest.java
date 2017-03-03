package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineExpressionTest {

    private static final String EXPRESSION_LEVEL_1 = "0";
    private static final String EXPRESSION_LEVEL_2 = "123.4";

    private BaselineExpression subject;

    @Test
    public void expressionLevel0 () {

        subject = new BaselineExpression(EXPRESSION_LEVEL_1, "g1");

        assertThat(subject.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

    }

    @Test
    public void expressionLevelNA () {

        subject = new BaselineExpression(EXPRESSION_LEVEL_2, "g1");

        assertThat(subject.getLevelAsString(), is(EXPRESSION_LEVEL_2));

    }

    @Test
    public void testRemoveTrailingZero() {
        assertThat(BaselineExpression.removeTrailingZero(1.111111), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.100111), is("1.1001"));
        assertThat(BaselineExpression.removeTrailingZero(1.111100), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.110000), is("1.11"));
    }

}
